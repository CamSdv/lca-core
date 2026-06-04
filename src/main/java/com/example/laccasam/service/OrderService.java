package com.example.laccasam.service;
import com.example.laccasam.dto.*;
import com.example.laccasam.entity.*;
import com.example.laccasam.enums.BatchStatus;
import com.example.laccasam.enums.MovementType;
import com.example.laccasam.enums.OrderStatus;
import com.example.laccasam.enums.PricingType;
import com.example.laccasam.exception.BadRequestException;
import com.example.laccasam.exception.NotFoundException;
import com.example.laccasam.mapper.OrderMapper;
import com.example.laccasam.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    private BatchService batchService;

    public OrderResponseDTO create(OrderRequestDTO dto) {

        Batch batch = batchService.getOrCreateTodayBatch();

        if (batch.getStatus() == BatchStatus.CLOSED) {
            throw new BadRequestException("Orders are closed for today");
        }

        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new BadRequestException("Order must have items");
        }

        Customer customer;

        if (dto.getCustomer() == null) {
            throw new BadRequestException("Customer is required");
        }

        if (dto.getCustomer().getId() != null) {

            customer = customerRepository.findById(dto.getCustomer().getId())
                    .orElseThrow(() -> new NotFoundException("Customer not found"));

        } else {

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


        Seller seller = sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new NotFoundException("Seller not found"));

        List<Product> products = productRepository.findAllById(
                dto.getItems().stream()
                        .map(OrderItemCreateDTO::getProductId)
                        .toList()
        );

        if (products.size() != dto.getItems().size()) {
            throw new NotFoundException("One or more products not found");
        }

        Order order = OrderMapper.toEntity(dto, customer, seller, products);
        order.setBatch(batch);
        order.setStatus(OrderStatus.CREATED);
        order = repository.save(order);

        return OrderMapper.toResponse(order);
    }

    public OrderResponseDTO updateWeights(Long id, List<OrderItemWeightDTO> itemsDto) {

        Order order = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        if (OrderStatus.CONFIRMED.equals(order.getStatus())) {
            throw new BadRequestException("Cannot modify confirmed order");
        }

        if (!OrderStatus.CREATED.equals(order.getStatus())) {
            throw new BadRequestException("Only CREATED orders can be adjusted");
        }

        List<OrderItem> weightItems = order.getItems().stream()
                .filter(i -> i.getProduct().getPricingType() == PricingType.WEIGHT)
                .toList();

        if (itemsDto.size() != weightItems.size()) {
            throw new BadRequestException("Only WEIGHT items must be provided");
        }

        Map<Long, Double> weightsMap = itemsDto.stream()
                .collect(Collectors.toMap(
                        OrderItemWeightDTO::getId,
                        OrderItemWeightDTO::getFinalWeight
                ));

        if (!weightsMap.keySet().containsAll(
                order.getItems().stream().map(OrderItem::getId).toList()
        )) {
            throw new BadRequestException("Some items are missing");
        }

        order.getItems().forEach(item -> {

            Product product = item.getProduct();

            if (PricingType.WEIGHT.equals(product.getPricingType())) {

                if (!weightsMap.containsKey(item.getId())) {
                    throw new BadRequestException("Weight required for item " + item.getId());
                }

                item.setFinalWeight(weightsMap.get(item.getId()));

            } else {
                item.setFinalWeight(null);
            }
        });

        order.setStatus(OrderStatus.ADJUSTED);

        Order saved = repository.save(order);
        return OrderMapper.toResponse(saved);
    }

    @Transactional
    public OrderResponseDTO confirm(Long id) {

        Order order = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        boolean requiresAdjustment = requiresWeightAdjustment(order);

        if (requiresAdjustment && !OrderStatus.ADJUSTED.equals(order.getStatus())) {
            throw new BadRequestException("Order must be ADJUSTED before confirmation");
        }

        if (!requiresAdjustment && !OrderStatus.CREATED.equals(order.getStatus())) {
            throw new BadRequestException("Invalid order state for UNIT products");
        }

        order.getItems().forEach(item -> {

            if (item.getProduct().getPricingType() == PricingType.WEIGHT && item.getFinalWeight() == null) {
                throw new BadRequestException("Final weight required for WEIGHT products");
            }

            Product product = item.getProduct();

            product.getProductSupplies().forEach(ps -> {

                Supply supply = ps.getSupply();

                double multiplier = PricingType.WEIGHT.equals(product.getPricingType())
                        ? item.getFinalWeight()
                        : item.getQuantity();

                double totalUsed = ps.getQuantityRequired() * multiplier;

                if (supply.getStock() < totalUsed) {
                    throw new BadRequestException("Not enough stock for " + supply.getPresentation());
                }

                supply.setStock(supply.getStock() - totalUsed);

            });
        });

        order.setStatus(OrderStatus.CONFIRMED);

        Order saved = repository.save(order);

        return OrderMapper.toResponse(saved);
    }

    public List<OrderResponseDTO> getCurrentBatchOrders() {

        Batch batch = batchService.getOrCreateTodayBatch();

        return batch.getOrders().stream()
                .map(OrderMapper::toResponse)
                .toList();
    }

    private boolean requiresWeightAdjustment(Order order) {
        return order.getItems().stream()
                .anyMatch(item -> item.getProduct().getPricingType() == PricingType.WEIGHT);
    }

    public List<OrderResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(OrderMapper::toResponse)
                .toList();
    }

}