package com.example.laccasam.mapper;

import com.example.laccasam.dto.*;
import com.example.laccasam.entity.*;
import com.example.laccasam.enums.OrderStatus;

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

        List<OrderItem> items = dto.getItems().stream().map(i -> {

            Product product = products.stream()
                    .filter(p -> p.getId().equals(i.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(i.getQuantity());
            item.setOrder(order);

            return item;

        }).collect(Collectors.toList());

        order.setItems(items);

        return order;
    }

    public static OrderResponseDTO toResponse(Order order) {

        OrderResponseDTO dto = new OrderResponseDTO();

        dto.setId(order.getId());
        dto.setStatus(OrderStatus.valueOf(String.valueOf(order.getStatus())));
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

        return dto;
    }
}

