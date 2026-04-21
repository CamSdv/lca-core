package com.example.laccasam.dto;
import com.example.laccasam.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OrderResponseDTO {

    private Long id;
    private OrderStatus status;
    private LocalDate deliveryDate;
    private String customerName;
    private String sellerName;
    private List<OrderItemResponseDTO> items;
    private Double total;

}