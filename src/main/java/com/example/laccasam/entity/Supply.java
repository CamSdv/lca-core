package com.example.laccasam.entity;

import com.example.laccasam.enums.SupplyCategory;
import com.example.laccasam.enums.SupplyType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"type", "category", "presentation"})
        }
)
public class Supply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SupplyType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SupplyCategory category;

    @Column(nullable = false)
    private String presentation; // 500G, 1L, etc


    @Column(nullable = false)
    private Boolean active = true;
}