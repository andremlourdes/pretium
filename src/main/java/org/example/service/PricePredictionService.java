package org.example.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.example.dto.PriceForecastDTO;
import org.example.entity.PriceHistory;
import org.example.entity.Product;
import org.example.repository.PriceHistoryRepository;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PricePredictionService {

    private final ProductRepository productRepository;
    private final PriceHistoryRepository priceHistoryRepository;

    public PricePredictionService(ProductRepository productRepository,
                                  PriceHistoryRepository priceHistoryRepository) {
        this.productRepository = productRepository;
        this.priceHistoryRepository = priceHistoryRepository;
    }

    /** Previsão baseline: média móvel dos últimos dez registros disponíveis. */
    public PriceForecastDTO forecast(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        List<PriceHistory> history = priceHistoryRepository.findByProduct(product).stream()
            .filter(item -> item.getPrice() != null)
            .sorted(Comparator.comparing(PriceHistory::getRecordedDate).reversed())
            .limit(10)
            .toList();
        if (history.isEmpty()) {
            throw new RuntimeException("Histórico insuficiente para previsão");
        }
        BigDecimal current = history.get(0).getPrice();
        BigDecimal average = history.stream()
            .map(PriceHistory::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .divide(BigDecimal.valueOf(history.size()), 2, RoundingMode.HALF_UP);
        String trend = average.compareTo(current) > 0 ? "DOWN" : average.compareTo(current) < 0 ? "UP" : "STABLE";
        return new PriceForecastDTO(product.getId(), product.getName(), current, average, trend,
            history.size(), LocalDateTime.now());
    }
}

