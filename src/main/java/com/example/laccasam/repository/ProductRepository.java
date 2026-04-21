package com.example.laccasam.repository;

import com.example.laccasam.entity.Product;
import com.example.laccasam.enums.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByNameAndCategoryAndPresentation(
            String name,
            ProductCategory category,
            String presentation
    );

    List<Product> findAllByActiveTrue();

}