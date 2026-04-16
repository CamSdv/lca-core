package com.example.laccasam.service;
import com.example.laccasam.dto.OrderItemCreateDTO;
import com.example.laccasam.dto.OrderItemWeightDTO;
import com.example.laccasam.dto.OrderRequestDTO;
import com.example.laccasam.dto.OrderResponseDTO;
import com.example.laccasam.entity.*;
import com.example.laccasam.enums.OrderStatus;
import com.example.laccasam.exception.BadRequestException;
import com.example.laccasam.exception.NotFoundException;
import com.example.laccasam.mapper.OrderMapper;
import com.example.laccasam.mapper.OrderResponseMapper;
import com.example.laccasam.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplyRepository supplyRepository;

    public OrderResponseDTO create(OrderRequestDTO dto) {

        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new NotFoundException("Order must have items");
        }

        // 🟢 1. Resolver cliente (nuevo o existente)
        Customer customer;

        if (dto.getCustomer() == null) {
            throw new BadRequestException("Customer is required");
        }

        if (dto.getCustomer().getId() != null) {

            // 🟢 Cliente existente
            customer = customerRepository.findById(dto.getCustomer().getId())
                    .orElseThrow(() -> new NotFoundException("Customer not found"));

        } else {

            // 🟢 Cliente nuevo
            if (dto.getCustomer().getName() == null || dto.getCustomer().getName().isBlank()) {
                throw new BadRequestException("Customer name is required");
            }

            if (dto.getCustomer().getPhone() == null || dto.getCustomer().getPhone().isBlank()) {
                throw new BadRequestException("Customer phone is required");
            }

            customer = new Customer();
            customer.setName(dto.getCustomer().getName());
            customer.setPhone(dto.getCustomer().getPhone());

            customer = customerRepository.save(customer);
        }

        // 🟢 2. Seller
        Seller seller = sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new NotFoundException("Seller not found"));

        // 🟢 3. Productos
        List<Product> products = productRepository.findAllById(
                dto.getItems().stream()
                        .map(OrderItemCreateDTO::getProductId)
                        .toList()
        );

        // 🟢 4. Mapear
        Order order = OrderMapper.toEntity(dto, customer, seller, products);



        order = repository.save(order);

        return OrderMapper.toResponse(order);
    }

    public Order updateWeights(Long id, List<OrderItemWeightDTO> itemsDto) {

        Order order = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        if (!OrderStatus.CREATED.equals(order.getStatus())) {
            throw new NotFoundException("Only CREATED orders can be adjusted");
        }

        Map<Long, Double> weightsMap = itemsDto.stream()
                .collect(Collectors.toMap(
                        OrderItemWeightDTO::getId,
                        OrderItemWeightDTO::getFinalWeight
                ));

        order.getItems().forEach(item -> {
            if (weightsMap.containsKey(item.getId())) {
                item.setFinalWeight(weightsMap.get(item.getId()));
            }
        });

        order.setStatus(OrderStatus.ADJUSTED);

        return repository.save(order);
    }

    @Transactional
    public OrderResponseDTO confirm(Long id) {

        Order order = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        if (!OrderStatus.ADJUSTED.equals(order.getStatus())) {
            throw new NotFoundException("Order must be ADJUSTED before confirmation");
        }

        order.getItems().forEach(item -> {

            if (item.getFinalWeight() == null) {
                throw new NotFoundException("Final weight missing for item " + item.getId());
            }

            Product product = item.getProduct();

            product.getProductSupplies().forEach(ps -> {

                Supply supply = ps.getSupply();

                double totalUsed = ps.getQuantityRequired() * item.getFinalWeight();

                if (supply.getStock() < totalUsed) {
                    throw new NotFoundException("Not enough stock for: " + supply.getName());
                }

                supply.setStock(supply.getStock() - totalUsed);
                supplyRepository.save(supply);
            });
        });

        order.setStatus(OrderStatus.CONFIRMED);

        Order saved = repository.save(order);

        return OrderMapper.toResponse(saved);
    }

}