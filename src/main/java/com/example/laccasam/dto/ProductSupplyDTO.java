package com.example.laccasam.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSupplyDTO {

    @NotNull
    private Long supplyId;

    @NotNull
    @Positive
    private Double quantityRequired;
}
