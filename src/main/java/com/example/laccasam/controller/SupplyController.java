package com.example.laccasam.controller;

import com.example.laccasam.dto.SupplyRequestDTO;
import com.example.laccasam.dto.SupplyResponseDTO;
import com.example.laccasam.service.SupplyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/supplies")
public class SupplyController {

    @Autowired
    private SupplyService service;

    @PostMapping
    public ResponseEntity<SupplyResponseDTO> create(@RequestBody @Valid SupplyRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public List<SupplyResponseDTO> getAll() {
        return service.findAll();
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<SupplyResponseDTO> updateStock(
            @PathVariable Long id,
            @RequestParam Double stock
    ) {
        return ResponseEntity.ok(service.updateStock(id, stock));
    }
}