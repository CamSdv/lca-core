package com.example.laccasam.dto;

import com.example.laccasam.enums.PricingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {
    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Presentation is required")
    private String presentation;

    @NotNull(message = "Pricing type is required")
    private PricingType pricingType;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;
}