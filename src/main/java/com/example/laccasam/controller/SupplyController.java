package com.example.laccasam.controller;

import com.example.laccasam.dto.SupplyRequestDTO;
import com.example.laccasam.dto.SupplyResponseDTO;
import com.example.laccasam.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplies")
public class SupplyController {

    @Autowired
    private SupplyService service;

    @PostMapping
    public SupplyResponseDTO create(@RequestBody SupplyRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<SupplyResponseDTO> findAll() {
        return service.findAll();
    }
}