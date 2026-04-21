package com.example.laccasam.controller;

import com.example.laccasam.dto.InventoryMovementResponseDTO;
import com.example.laccasam.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService service;

    @GetMapping("/movements")
    public List<InventoryMovementResponseDTO> getMovements(
            @RequestParam(required = false) Long supplyId
    ) {
        return service.getAllMovements(supplyId);
    }
}
