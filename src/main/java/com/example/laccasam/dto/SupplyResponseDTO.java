package com.example.laccasam.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SupplyResponseDTO {
    private Long id;
    private String name;
    private Double stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}