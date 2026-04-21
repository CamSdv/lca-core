package com.example.laccasam.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BatchResponseDTO {
    private Long id;
    private LocalDate date;
    private String status;
}
