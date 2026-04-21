package com.example.laccasam.mapper;

import com.example.laccasam.dto.SupplyRequestDTO;
import com.example.laccasam.dto.SupplyResponseDTO;
import com.example.laccasam.entity.Supply;

public class SupplyMapper {

    public static Supply toEntity(SupplyRequestDTO dto) {

        Supply s = new Supply();

        s.setType(dto.getType());
        s.setCategory(dto.getCategory());
        s.setPresentation(dto.getPresentation().trim().toUpperCase());
        s.setStock(dto.getStock());
        s.setActive(true);

        return s;
    }

    public static SupplyResponseDTO toResponse(Supply s) {

        SupplyResponseDTO dto = new SupplyResponseDTO();

        dto.setId(s.getId());
        dto.setType(s.getType());
        dto.setCategory(s.getCategory());
        dto.setPresentation(s.getPresentation());
        dto.setStock(s.getStock());
        dto.setCreatedAt(s.getCreatedAt());
        dto.setUpdatedAt(s.getUpdatedAt());

        return dto;
    }
}