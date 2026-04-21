package com.example.laccasam.service;

import com.example.laccasam.entity.*;
import com.example.laccasam.enums.BatchStatus;
import com.example.laccasam.enums.OrderStatus;
import com.example.laccasam.enums.PricingType;
import com.example.laccasam.exception.BadRequestException;
import com.example.laccasam.repository.BatchRepository;
import com.example.laccasam.repository.OrderRepository;
import com.example.laccasam.repository.SupplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class BatchService {

    private final BatchRepository batchRepository;
    private final OrderRepository orderRepository;
    private final SupplyRepository supplyRepository;

    public Batch getOrCreateTodayBatch() {

        LocalDate today = LocalDate.now();

        return batchRepository.findByDateAndStatus(today, BatchStatus.OPEN)
                .orElseGet(() -> {
                    Batch batch = new Batch();
                    batch.setDate(today);
                    batch.setStatus(BatchStatus.OPEN);
                    return batchRepository.save(batch);
                });
    }

    public Order createOrder(Order order) {

        Batch batch = getOrCreateTodayBatch();

        order.setBatch(batch);

        return orderRepository.save(order);
    }

    public void closeBatch(Long batchId) {

        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        if (batch.getStatus() == BatchStatus.CLOSED) {
            throw new RuntimeException("Batch already closed");
        }

        if (batch.getOrders().stream()
                .anyMatch(o -> o.getStatus() != OrderStatus.CONFIRMED)) {
            throw new BadRequestException("All orders must be CONFIRMED");
        }

        batch.setStatus(BatchStatus.CLOSED);
        batchRepository.save(batch);
    }

    public Map<Long, Double> calculateRequiredSupplies(Batch batch) {

        Map<Long, Double> supplyMap = new HashMap<>();

        for (Order order : batch.getOrders()) {

            for (OrderItem item : order.getItems()) {

                Product product = item.getProduct();
                int quantity = item.getQuantity();

                for (ProductSupply ps : product.getProductSupplies()) {

                    Long supplyId = ps.getSupply().getId();

                    double multiplier = product.getPricingType() == PricingType.WEIGHT
                            ? item.getFinalWeight()
                            : item.getQuantity();

                    double totalRequired = ps.getQuantityRequired() * multiplier;

                    supplyMap.merge(
                            supplyId,
                            totalRequired,
                            Double::sum
                    );
                }
            }
        }

        return supplyMap;
    }

    public Map<String, Double> getSupplyReport(Long batchId) {

        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        Map<Long, Double> raw = calculateRequiredSupplies(batch);

        Map<String, Double> result = new HashMap<>();

        for (Map.Entry<Long, Double> entry : raw.entrySet()) {

            Supply supply = supplyRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Supply not found"));

            String key = supply.getType() + " - " +
                    supply.getCategory() + " - " +
                    supply.getPresentation();

            result.put(key, entry.getValue());
        }

        return result;
    }
}
