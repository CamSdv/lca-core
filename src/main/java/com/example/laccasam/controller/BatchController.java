package com.example.laccasam.controller;

import com.example.laccasam.dto.BatchResponseDTO;
import com.example.laccasam.entity.Batch;
import com.example.laccasam.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/batch")
public class BatchController {

    @Autowired
    private BatchService service;

    @PutMapping("/{id}/close")
    public void close(@PathVariable Long id) {
        service.closeBatch(id);
    }

    @GetMapping("/current")
    public BatchResponseDTO current() {
        Batch batch = service.getOrCreateTodayBatch();

        BatchResponseDTO dto = new BatchResponseDTO();
        dto.setId(batch.getId());
        dto.setDate(batch.getDate());
        dto.setStatus(batch.getStatus().name());

        return dto;
    }

    @GetMapping("/{id}/report")
    public Map<String, Double> report(@PathVariable Long id) {
        return service.getSupplyReport(id);
    }
}
