package com.example.laccasam.controller;

import com.example.laccasam.dto.SellerRequestDTO;
import com.example.laccasam.dto.SellerResponseDTO;
import com.example.laccasam.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    @Autowired
    private SellerService service;

    @PostMapping
    public SellerResponseDTO create(@RequestBody SellerRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<SellerResponseDTO> findAll() {
        return service.findAll();
    }
}