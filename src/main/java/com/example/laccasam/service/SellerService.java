package com.example.laccasam.service;

import com.example.laccasam.dto.SellerRequestDTO;
import com.example.laccasam.dto.SellerResponseDTO;
import com.example.laccasam.entity.Seller;
import com.example.laccasam.exception.NotFoundException;
import com.example.laccasam.mapper.SellerMapper;
import com.example.laccasam.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerService {

    @Autowired
    private SellerRepository repository;

    public SellerResponseDTO create(SellerRequestDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new NotFoundException("Seller name is required");
        }

        Seller seller = SellerMapper.toEntity(dto);
        Seller saved = repository.save(seller);

        return SellerMapper.toResponse(saved);
    }

    public List<SellerResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(SellerMapper::toResponse)
                .collect(Collectors.toList());
    }
}