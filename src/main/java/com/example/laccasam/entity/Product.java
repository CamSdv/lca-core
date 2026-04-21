package com.example.laccasam.entity;

import com.example.laccasam.enums.PricingType;
import com.example.laccasam.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Product extends BaseEntity {

    public void addProductSupply(ProductSupply ps) {
        productSupplies.add(ps);
        ps.setProduct(this);
    }

    public void removeProductSupply(ProductSupply ps) {
        productSupplies.remove(ps);
        ps.setProduct(null);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategory category;

    @Column(nullable = false)
    private String presentation;

    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PricingType pricingType;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductSupply> productSupplies = new ArrayList<>();

}