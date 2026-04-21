package com.example.laccasam.entity;

import com.example.laccasam.enums.MovementType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class InventoryMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supply_id", nullable = false)
    private Supply supply;

    private Double quantity;

    @Enumerated(EnumType.STRING)
    private MovementType type;

    private LocalDateTime date;

    private Long reference;
}