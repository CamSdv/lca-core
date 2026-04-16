package com.example.laccasam.service;

import com.example.laccasam.dto.SupplyRequestDTO;
import com.example.laccasam.dto.SupplyResponseDTO;
import com.example.laccasam.entity.Supply;
import com.example.laccasam.exception.NotFoundException;
import com.example.laccasam.mapper.SupplyMapper;
import com.example.laccasam.repository.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplyService {

    @Autowired
    private SupplyRepository repository;

    public SupplyResponseDTO create(SupplyRequestDTO dto) {

        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new NotFoundException("Supply name is required");
        }

        if (dto.getStock() == null || dto.getStock() < 0) {
            throw new NotFoundException("Stock must be >= 0");
        }

        Supply supply = SupplyMapper.toEntity(dto);
        Supply saved = repository.save(supply);

        return SupplyMapper.toResponse(saved);
    }

    public List<SupplyResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(SupplyMapper::toResponse)
                .collect(Collectors.toList());
    }
}