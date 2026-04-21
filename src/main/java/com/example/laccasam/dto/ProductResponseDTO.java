package com.example.laccasam.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String category;
    private String presentation;
    private String pricingType;
    private Double price;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}