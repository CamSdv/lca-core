package com.example.laccasam.dto;

import com.example.laccasam.enums.PricingType;
import com.example.laccasam.enums.ProductCategory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Category is required")
    private ProductCategory category;

    @NotBlank(message = "Presentation is required")
    private String presentation;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Pricing type is required")
    private PricingType pricingType;

    @Valid
    private List<ProductSupplyDTO> supplies;
}