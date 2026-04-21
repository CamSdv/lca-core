package com.example.laccasam.repository;

import com.example.laccasam.entity.Batch;
import com.example.laccasam.enums.BatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface BatchRepository extends JpaRepository<Batch, Long> {

    Optional<Batch> findByDateAndStatus(LocalDate date, BatchStatus status);
}
