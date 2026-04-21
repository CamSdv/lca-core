package com.example.laccasam.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InventoryMovementResponseDTO {

    private Long id;
    private String supplyName;
    private Double quantity;
    private String type;
    private LocalDateTime date;
    private Long reference;
    private Double balance;
}