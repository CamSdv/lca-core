package com.example.laccasam.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Supply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private Double stock;

    @OneToMany(mappedBy = "supply")
    private List<ProductSupply> productSupplies;
}