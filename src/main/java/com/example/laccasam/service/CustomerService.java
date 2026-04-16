package com.example.laccasam.service;

import com.example.laccasam.dto.CustomerRequestDTO;
import com.example.laccasam.dto.CustomerResponseDTO;
import com.example.laccasam.entity.Customer;
import com.example.laccasam.exception.NotFoundException;
import com.example.laccasam.mapper.CustomerMapper;
import com.example.laccasam.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public CustomerResponseDTO create(CustomerRequestDTO dto) {

        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new NotFoundException("Customer name is required");
        }

        Customer customer = CustomerMapper.toEntity(dto);

        Customer saved = repository.save(customer);

        return CustomerMapper.toResponse(saved);
    }

    public List<CustomerResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(CustomerMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponseDTO findById(Long id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        return CustomerMapper.toResponse(customer);
    }
}