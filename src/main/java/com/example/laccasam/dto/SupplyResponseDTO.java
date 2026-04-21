package com.example.laccasam.dto;

import com.example.laccasam.enums.SupplyCategory;
import com.example.laccasam.enums.SupplyType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SupplyResponseDTO {
    private Long id;
    private SupplyType type;
    private SupplyCategory category;
    private String presentation;
    private Double stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}