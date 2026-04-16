package com.example.laccasam.entity;

import com.example.laccasam.enums.PricingType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;           // Yogurt, Queso
    private String category;       // Mora, Paipa
    private String presentation;   // 500g, 1L
    @Enumerated(EnumType.STRING)
    private PricingType pricingType;
    private Double price;          // valor base

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductSupply> productSupplies;

}