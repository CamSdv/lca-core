package com.example.laccasam.repository;

import com.example.laccasam.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    List<Seller> findAllByOrderByNameAsc();
    List<Seller> findByActiveOrderByIdAsc(Boolean active);
}