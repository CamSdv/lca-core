package com.example.laccasam.service;

import com.example.laccasam.dto.SupplyRequestDTO;
import com.example.laccasam.dto.SupplyResponseDTO;
import com.example.laccasam.entity.Supply;
import com.example.laccasam.exception.BadRequestException;
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

        String presentation = dto.getPresentation().trim().toUpperCase();

        repository.findByTypeAndCategoryAndPresentation(
                dto.getType(),
                dto.getCategory(),
                presentation
        ).ifPresent(s -> {
            throw new BadRequestException("Supply already exists");
        });

        Supply supply = SupplyMapper.toEntity(dto);

        supply = repository.save(supply);

        return SupplyMapper.toResponse(supply);
    }

    public List<SupplyResponseDTO> findAll() {
        return repository.findAllByOrderByTypeAscCategoryAsc()
                .stream()
                .map(SupplyMapper::toResponse)
                .toList();
    }

    public SupplyResponseDTO updateStock(Long id, Double stock) {

        Supply s = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supply not found"));

        if (stock < 0) {
            throw new BadRequestException("Stock cannot be negative");
        }

        s.setStock(stock);

        return SupplyMapper.toResponse(repository.save(s));
    }
}