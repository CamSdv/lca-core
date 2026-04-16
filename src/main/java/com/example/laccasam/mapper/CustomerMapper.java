package com.example.laccasam.mapper;
import com.example.laccasam.dto.CustomerRequestDTO;
import com.example.laccasam.dto.CustomerResponseDTO;
import com.example.laccasam.entity.Customer;

public class CustomerMapper {

    public static Customer toEntity( CustomerRequestDTO dto){
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setPhone(dto.getPhone());
        return customer;
    }

    public static CustomerResponseDTO toResponse(Customer entity){
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPhone(entity.getPhone());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
