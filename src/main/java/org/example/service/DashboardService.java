package org.example.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.example.dto.DashboardSummaryDTO;
import org.example.repository.ProductRepository;
import org.example.repository.ReceiptRepository;
import org.example.repository.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private static final Logger log = LoggerFactory.getLogger(DashboardService.class);

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final ReceiptRepository receiptRepository;

    public DashboardService(ProductRepository productRepository,
                            StoreRepository storeRepository,
                            ReceiptRepository receiptRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.receiptRepository = receiptRepository;
    }

    @Cacheable("dashboardSummary")
    public DashboardSummaryDTO summary() {
        log.info("Generating dashboard summary...");
        BigDecimal totalSpent = receiptRepository.findAll().stream()
            .map(receipt -> receipt.getTotalAmount() == null ? BigDecimal.ZERO : receipt.getTotalAmount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("Dashboard summary generated");
        return new DashboardSummaryDTO(
            productRepository.count(),
            storeRepository.count(),
            receiptRepository.count(),
            totalSpent,
            LocalDateTime.now()
        );
    }
}
