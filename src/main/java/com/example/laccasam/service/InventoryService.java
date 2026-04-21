package com.example.laccasam.service;

import com.example.laccasam.dto.InventoryMovementResponseDTO;
import com.example.laccasam.entity.InventoryMovement;
import com.example.laccasam.repository.InventoryMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryMovementRepository repository;

    public List<InventoryMovementResponseDTO> getAllMovements(Long supplyId) {

        List<InventoryMovement> movements;

        if (supplyId != null) {
            movements = repository.findBySupplyIdOrderByDateAsc(supplyId);
        } else {
            movements = repository.findAllByOrderByDateAsc();
        }

        double balance = 0;

        List<InventoryMovementResponseDTO> response = new ArrayList<>();

        for (InventoryMovement m : movements) {

            InventoryMovementResponseDTO dto = new InventoryMovementResponseDTO();

            String supplyName = m.getSupply().getType() + " - " +
                    m.getSupply().getCategory() + " - " +
                    m.getSupply().getPresentation();

            dto.setId(m.getId());
            dto.setSupplyName(supplyName);
            dto.setQuantity(m.getQuantity());
            dto.setType(m.getType().name());
            dto.setDate(m.getDate());
            dto.setReference(m.getReference());

            // 🔥 lógica kardex correcta
            if (m.getType().name().equals("IN")) {
                balance += m.getQuantity();
            } else {
                balance -= m.getQuantity();
            }

            dto.setBalance(balance);

            response.add(dto);
        }

        return response;
    }
}
