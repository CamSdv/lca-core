package com.example.laccasam.controller;

import com.example.laccasam.dto.ProductRequestDTO;
import com.example.laccasam.dto.ProductResponseDTO;
import com.example.laccasam.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    public ProductResponseDTO create(@Valid @RequestBody ProductRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<ProductResponseDTO> getAllActive() {
        return service.findAllActive();
    }

    @PutMapping("/{id}")
    public ProductResponseDTO update( @PathVariable Long id, @Valid @RequestBody ProductRequestDTO dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}/toggle")
    public void toggle(@PathVariable Long id) {
        service.toggleActive(id);
    }
}