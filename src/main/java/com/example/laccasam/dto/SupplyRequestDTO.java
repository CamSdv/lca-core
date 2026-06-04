package com.example.laccasam.dto;

import com.example.laccasam.enums.SupplyCategory;
import com.example.laccasam.enums.SupplyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

    @Getter
    @Setter
    public class SupplyRequestDTO {
        @NotNull(message = "SupplyId is required")
        private SupplyType type;

        @NotNull
        private SupplyCategory category;

        @NotBlank
        private String presentation;

    }