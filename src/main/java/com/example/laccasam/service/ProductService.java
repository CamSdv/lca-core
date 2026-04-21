package com.example.laccasam.service;

import com.example.laccasam.dto.ProductRequestDTO;
import com.example.laccasam.dto.ProductResponseDTO;
import com.example.laccasam.entity.Product;
import com.example.laccasam.entity.ProductSupply;
import com.example.laccasam.entity.Supply;
import com.example.laccasam.exception.BadRequestException;
import com.example.laccasam.exception.NotFoundException;
import com.example.laccasam.mapper.ProductMapper;
import com.example.laccasam.repository.ProductRepository;
import com.example.laccasam.repository.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private SupplyRepository supplyRepository;

    public ProductResponseDTO create(ProductRequestDTO dto) {

        // 🟢 1. VALIDACIÓN DE NEGOCIO (UNIQUE)
        boolean exists = repository.existsByNameAndCategoryAndPresentation(
                dto.getName().trim().toUpperCase(),
                dto.getCategory(),
                dto.getPresentation().trim().toUpperCase()
        );

        if (exists) {
            throw new BadRequestException("Product already exists with same name, category and presentation");
        }

        // 🟢 2. CREAR PRODUCTO
        Product product = new Product();

        product.setName(dto.getName().trim().toUpperCase());
        product.setCategory(dto.getCategory());
        product.setPresentation(dto.getPresentation().trim().toUpperCase());
        product.setPricingType(dto.getPricingType());
        product.setPrice(dto.getPrice());
        product.setActive(true);

        // 🔴 IMPORTANTE: guardar primero
        Product savedProduct = repository.save(product);

        // 🟢 3. VALIDAR SUPPLIES
        if (dto.getSupplies() == null || dto.getSupplies().isEmpty()) {
            throw new BadRequestException("Product must have at least one supply");
        }

        // 🟢 4. CREAR RELACIÓN ProductSupply
        dto.getSupplies().forEach(s -> {

            if (s.getSupplyId() == null) {
                throw new BadRequestException("SupplyId is required");
            }

            Supply supply = supplyRepository.findById(s.getSupplyId())
                    .orElseThrow(() -> new NotFoundException("Supply not found: " + s.getSupplyId()));

            if (s.getQuantityRequired() == null || s.getQuantityRequired() <= 0) {
                throw new BadRequestException("Quantity required must be greater than 0");
            }

            ProductSupply ps = new ProductSupply();
            ps.setSupply(supply);
            ps.setQuantityRequired(s.getQuantityRequired());

            savedProduct.addProductSupply(ps); // ✅ CLAVE
        });

        Product finalProduct = repository.save(savedProduct);

        return ProductMapper.toResponse(finalProduct);
    }

    public List<ProductResponseDTO> findAllActive() {
        return repository.findAllByActiveTrue()
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setPresentation(dto.getPresentation());
        product.setPrice(dto.getPrice());
        product.setPricingType(dto.getPricingType());

        return ProductMapper.toResponse(repository.save(product));
    }

    public void toggleActive(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        product.setActive(!product.getActive());

        repository.save(product);
    }
}