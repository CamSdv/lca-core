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
        product.setPricingType(dto.getPricingType());
        product.setPrice(dto.getPrice());
        product.setActive(true);

        return product;
    }

    public static ProductResponseDTO toResponse(Product product) {

        ProductResponseDTO dto = new ProductResponseDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCategory(product.getCategory().name());
        dto.setPricingType(product.getPricingType().name());
        dto.setPresentation(product.getPresentation());
        dto.setPrice(product.getPrice());
        dto.setActive(product.getActive());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        return dto;
    }
}