package com.example.laccasam.mapper;

import com.example.laccasam.dto.*;
import com.example.laccasam.entity.*;
import com.example.laccasam.enums.OrderStatus;
import com.example.laccasam.enums.PricingType;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static Order toEntity(
            OrderRequestDTO dto,
            Customer customer,
            Seller seller,
            List<Product> products
    ) {

        Order order = new Order();
        order.setCustomer(customer);
        order.setSeller(seller);
        order.setDeliveryDate(dto.getDeliveryDate());
        order.setStatus(OrderStatus.CREATED);

        List<OrderItem> items = dto.getItems().stream().map(itemDto -> {

            Product product = products.stream()
                    .filter(p -> p.getId().equals(itemDto.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            item.setOrder(order);

            return item;

        }).collect(Collectors.toList());

        order.setItems(items);

        return order;
    }

    public static OrderResponseDTO toResponse(Order order) {

        OrderResponseDTO dto = new OrderResponseDTO();

        dto.setId(order.getId());
        dto.setStatus(order.getStatus());
        dto.setDeliveryDate(order.getDeliveryDate());

        dto.setCustomerName(order.getCustomer().getName());
        dto.setSellerName(order.getSeller().getName());

        List<OrderItemResponseDTO> items = order.getItems().stream().map(item -> {

            OrderItemResponseDTO itemDto = new OrderItemResponseDTO();

            itemDto.setId(item.getId());
            itemDto.setProductName(item.getProduct().getName());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setFinalWeight(item.getFinalWeight());

            return itemDto;

        }).toList();

        dto.setItems(items);

        double total = order.getItems().stream()
                .mapToDouble(item -> {
                    if (item.getProduct().getPricingType() == PricingType.WEIGHT) {
                        return item.getFinalWeight() * item.getProduct().getPrice();
                    } else {
                        return item.getQuantity() * item.getProduct().getPrice();
                    }
                }).sum();

        dto.setTotal(total);

        return dto;
    }
}

