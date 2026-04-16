package com.example.laccasam.mapper;

import com.example.laccasam.dto.SupplyRequestDTO;
import com.example.laccasam.dto.SupplyResponseDTO;
import com.example.laccasam.entity.Supply;

public class SupplyMapper {

    public static Supply toEntity(SupplyRequestDTO dto) {
        Supply supply = new Supply();
        supply.setName(dto.getName());
        supply.setStock(dto.getStock());
        return supply;
    }

    public static SupplyResponseDTO toResponse(Supply entity) {
        SupplyResponseDTO dto = new SupplyResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStock(entity.getStock());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}