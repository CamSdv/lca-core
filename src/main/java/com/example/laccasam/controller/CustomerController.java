package com.example.laccasam.controller;

import com.example.laccasam.dto.CustomerRequestDTO;
import com.example.laccasam.dto.CustomerResponseDTO;
import com.example.laccasam.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping
    public CustomerResponseDTO create(@RequestBody @Valid CustomerRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<CustomerResponseDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CustomerResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }
}
