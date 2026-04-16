package com.example.laccasam.mapper;

import com.example.laccasam.dto.ProductRequestDTO;
import com.example.laccasam.dto.ProductResponseDTO;
import com.example.laccasam.entity.Product;
import com.example.laccasam.enums.PricingType;

public class ProductMapper {

    public static Product toEntity(ProductRequestDTO dto) {

        Product product = new Product();
        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setPresentation(dto.getPresentation());
        product.setPricingType(PricingType.valueOf(dto.getPricingType().toString()));
        product.setPrice(dto.getPrice());

        return product;
    }

    public static ProductResponseDTO toResponse(Product product) {

        ProductResponseDTO dto = new ProductResponseDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCategory(product.getCategory());
        dto.setPresentation(product.getPresentation());
        dto.setPricingType(String.valueOf(product.getPricingType()));
        dto.setPrice(product.getPrice());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        return dto;
    }
}