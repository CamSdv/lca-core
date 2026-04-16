package com.example.laccasam.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequestDTO {
    @NotNull(message = "Name is required")
    private String name;
    private String phone;
}
