package com.example.laccasam.repository;

import com.example.laccasam.entity.Supply;
import com.example.laccasam.enums.SupplyCategory;
import com.example.laccasam.enums.SupplyType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SupplyRepository extends JpaRepository<Supply, Long> {

    Optional<Supply> findByTypeAndCategoryAndPresentation(
            SupplyType type,
            SupplyCategory category,
            String presentation
    );

    List<Supply> findAllByActiveTrue();
    List<Supply> findAllByOrderByTypeAscCategoryAsc();
}
