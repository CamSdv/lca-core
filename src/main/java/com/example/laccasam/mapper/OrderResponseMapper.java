package com.example.laccasam.mapper;

import com.example.laccasam.dto.OrderItemResponseDTO;
import com.example.laccasam.dto.OrderResponseDTO;
import com.example.laccasam.entity.Order;
import java.util.List;

public class OrderResponseMapper {

    public static OrderResponseDTO toEntity(Order order) {

        OrderResponseDTO dto = new OrderResponseDTO();

        dto.setId(order.getId());
        dto.setStatus(order.getStatus());
        dto.setDeliveryDate(order.getDeliveryDate());
        dto.setCustomerName(order.getCustomer().getName());
        dto.setSellerName(order.getSeller().getName());

        List<OrderItemResponseDTO> items = order.getItems().stream().map(item -> {

            OrderItemResponseDTO i = new OrderItemResponseDTO();

            i.setId(item.getId());
            i.setProductName(item.getProduct().getName());
            i.setQuantity(item.getQuantity());
            i.setFinalWeight(item.getFinalWeight());

            return i;

        }).toList();

        dto.setItems(items);

        return dto;
    }
}