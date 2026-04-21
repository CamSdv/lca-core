package com.example.laccasam.dto;

import com.example.laccasam.entity.Supply;
import com.example.laccasam.enums.MovementType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class InventoryMovementDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Supply supply;

    private Double quantity;

    @Enumerated(EnumType.STRING)
    private MovementType type;

    private LocalDateTime date;

    private Long reference;
}
