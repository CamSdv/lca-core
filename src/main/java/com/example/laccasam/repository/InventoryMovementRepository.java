package com.example.laccasam.repository;

import com.example.laccasam.dto.InventoryMovementDTO;
import com.example.laccasam.entity.InventoryMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Long> {

    List<InventoryMovement> findAllByOrderByDateAsc();

    List<InventoryMovement> findBySupplyIdOrderByDateAsc(Long supplyId);
}
