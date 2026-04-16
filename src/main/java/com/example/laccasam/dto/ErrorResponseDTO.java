package com.example.laccasam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResponseDTO {

    private String message;
    private int status;
    private LocalDateTime timestamp;
    private List<String> errors;
}