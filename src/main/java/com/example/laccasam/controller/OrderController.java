package com.example.laccasam.controller;

import com.example.laccasam.dto.OrderItemWeightDTO;
import com.example.laccasam.dto.OrderRequestDTO;
import com.example.laccasam.dto.OrderResponseDTO;
import com.example.laccasam.entity.Order;
import com.example.laccasam.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping
    public OrderResponseDTO create(@Valid @RequestBody OrderRequestDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}/weights")
    public OrderResponseDTO updateWeights(@PathVariable Long id, @Valid @RequestBody List<@Valid OrderItemWeightDTO> items) {
        return service.updateWeights(id, items);
    }

    @PutMapping("/{id}/confirm")
    public OrderResponseDTO confirm(@PathVariable Long id) {
        return service.confirm(id);
    }

    @GetMapping("/batch/current")
    public List<OrderResponseDTO> getCurrentBatchOrders() {
        return service.getCurrentBatchOrders();
    }

    @GetMapping
    public List<OrderResponseDTO> getAll() {
        return service.getAll();
    }

}
