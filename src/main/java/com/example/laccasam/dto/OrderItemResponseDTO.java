package com.example.laccasam.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponseDTO {

    private Long id;
    private String productName;
    private Integer quantity;
    private Double finalWeight;
}