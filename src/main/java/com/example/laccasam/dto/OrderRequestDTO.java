package com.example.laccasam.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {

    @NotNull(message = "Customer is required")
    @Valid
    private CustomerDTO customer;

    @NotNull(message = "SellerId is required")
    private Long sellerId;

    @NotNull(message = "Delivery date is required")
    @FutureOrPresent(message = "Delivery date must be today or future")
    private LocalDate deliveryDate;

    @NotEmpty(message = "Order must contain at least one item")
    @Valid
    private List<OrderItemCreateDTO> items;
}