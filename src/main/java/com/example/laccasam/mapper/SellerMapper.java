package com.example.laccasam.mapper;

import com.example.laccasam.dto.SellerRequestDTO;
import com.example.laccasam.dto.SellerResponseDTO;
import com.example.laccasam.entity.Seller;

public class SellerMapper {
    public static Seller toEntity(SellerRequestDTO dto) {
        Seller seller = new Seller();
        seller.setName(dto.getName());
        return seller;
    }

    public static SellerResponseDTO toResponse(Seller entity) {
        SellerResponseDTO dto = new SellerResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
