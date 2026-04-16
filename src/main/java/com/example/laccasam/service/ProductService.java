package com.example.laccasam.service;

import com.example.laccasam.dto.ProductRequestDTO;
import com.example.laccasam.dto.ProductResponseDTO;
import com.example.laccasam.entity.Product;
import com.example.laccasam.mapper.ProductMapper;
import com.example.laccasam.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public ProductResponseDTO create(ProductRequestDTO dto) {

        Product product = ProductMapper.toEntity(dto);

        product = repository.save(product);

        return ProductMapper.toResponse(product);
    }

    public List<ProductResponseDTO> findAll() {

        return repository.findAll().stream()
                .map(ProductMapper::toResponse)
                .toList();
    }
}