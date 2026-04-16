package com.example.laccasam.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemWeightDTO {

    @NotNull(message = "Item ID is required")
    private Long id;

    @NotNull(message = "Final weight is required")
    @Positive(message = "Final weight must be positive")
    private Double finalWeight;
}
