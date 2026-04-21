package com.example.laccasam.service;

import com.example.laccasam.dto.SellerRequestDTO;
import com.example.laccasam.dto.SellerResponseDTO;
import com.example.laccasam.entity.Seller;
import com.example.laccasam.exception.BadRequestException;
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
            throw new BadRequestException("Seller name is required");
        }

        Seller seller = new Seller();
        seller.setName(dto.getName().trim().toUpperCase());
        seller.setActive(true);

        Seller saved = repository.save(seller);

        return SellerMapper.toResponse(saved);
    }

    public List<SellerResponseDTO> findAll() {
        return repository.findAllByOrderByNameAsc()
                .stream()
                .map(SellerMapper::toResponse)
                .collect(Collectors.toList());
    }

    public void updateStatus(Long id, Boolean status) {
        Seller seller = repository.findById(id).orElseThrow(() -> new RuntimeException("No existe el vendedor seleccionado"));
        seller.setActive(status);
        repository.save(seller);
    }

    public List<SellerResponseDTO> findAllByStatus(Boolean status) {
        return repository.findByActiveOrderByIdAsc(status)
                .stream()
                .map(SellerMapper::toResponse)
                .collect(Collectors.toList());
    }
}