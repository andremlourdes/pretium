package org.example.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.example.dto.PriceAlertDTO;
import org.example.entity.PriceAlert;
import org.example.entity.PriceHistory;
import org.example.entity.Product;
import org.example.repository.PriceAlertRepository;
import org.example.repository.PriceHistoryRepository;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PriceAlertService {

    private final PriceAlertRepository alertRepository;
    private final ProductRepository productRepository;
    private final PriceHistoryRepository priceHistoryRepository;

    public PriceAlertService(PriceAlertRepository alertRepository,
                             ProductRepository productRepository,
                             PriceHistoryRepository priceHistoryRepository) {
        this.alertRepository = alertRepository;
        this.productRepository = productRepository;
        this.priceHistoryRepository = priceHistoryRepository;
    }

    public PriceAlertDTO create(Long productId, BigDecimal targetPrice, String conditionType) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        PriceAlert alert = new PriceAlert();
        alert.setProduct(product);
        alert.setTargetPrice(targetPrice);
        alert.setConditionType(conditionType == null ? "BELOW" : conditionType.toUpperCase());
        alert.setActive(true);
        alert.setCreatedAt(LocalDateTime.now());
        return toDTO(alertRepository.save(alert));
    }

    @Transactional(readOnly = true)
    public List<PriceAlertDTO> findAll() {
        return alertRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PriceAlertDTO findById(Long id) {
        return toDTO(alertRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Alerta não encontrado")));
    }

    public PriceAlertDTO deactivate(Long id) {
        PriceAlert alert = alertRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Alerta não encontrado"));
        alert.setActive(false);
        return toDTO(alertRepository.save(alert));
    }

    public void checkActiveAlerts() {
        for (PriceAlert alert : alertRepository.findByActiveTrue()) {
            PriceHistory latest = priceHistoryRepository.findFirstByProductOrderByRecordedDateDesc(alert.getProduct());
            if (latest != null && matches(alert, latest.getPrice())) {
                alert.setLastNotifiedAt(LocalDateTime.now());
                alertRepository.save(alert);
            }
        }
    }

    private boolean matches(PriceAlert alert, BigDecimal currentPrice) {
        int comparison = currentPrice.compareTo(alert.getTargetPrice());
        return switch (alert.getConditionType()) {
            case "ABOVE" -> comparison > 0;
            case "EXACT" -> comparison == 0;
            default -> comparison < 0;
        };
    }

    private PriceAlertDTO toDTO(PriceAlert alert) {
        PriceAlertDTO dto = new PriceAlertDTO();
        dto.setId(alert.getId());
        dto.setProductId(alert.getProduct().getId());
        dto.setProductName(alert.getProduct().getName());
        dto.setTargetPrice(alert.getTargetPrice());
        dto.setConditionType(alert.getConditionType());
        dto.setActive(alert.getActive());
        dto.setCreatedAt(alert.getCreatedAt());
        dto.setLastNotifiedAt(alert.getLastNotifiedAt());
        return dto;
    }
}

