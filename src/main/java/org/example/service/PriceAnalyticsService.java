package org.example.service;

import org.example.dto.PriceAnalyticsDTO;
import org.example.dto.PriceComparisonDTO;
import org.example.entity.Product;
import org.example.entity.PriceHistory;
import org.example.entity.Store;
import org.example.repository.PriceHistoryRepository;
import org.example.repository.ProductRepository;
import org.example.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Serviço para análise e comparação de preços.
 */
@Service
@Transactional(readOnly = true)
public class PriceAnalyticsService {

    private static final Logger log = LoggerFactory.getLogger(PriceAnalyticsService.class);

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    /**
     * Obtem análise completa de preços de um produto.
     */
    public PriceAnalyticsDTO analyzeProductPrice(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        List<PriceHistory> prices = priceHistoryRepository.findByProduct(product);

        if (prices.isEmpty()) {
            throw new RuntimeException("Nenhum histórico de preços encontrado para este produto");
        }

        // Pega o preço mais recente de cada loja
        Map<Store, PriceHistory> latestPricesByStore = prices.stream()
            .collect(Collectors.groupingBy(
                PriceHistory::getStore,
                Collectors.maxBy(Comparator.comparing(PriceHistory::getRecordedDate))
            ))
            .values()
            .stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toMap(PriceHistory::getStore, p -> p));

        List<BigDecimal> priceValues = latestPricesByStore.values().stream()
            .map(PriceHistory::getPrice)
            .sorted()
            .collect(Collectors.toList());

        BigDecimal minPrice = priceValues.get(0);
        BigDecimal maxPrice = priceValues.get(priceValues.size() - 1);
        BigDecimal avgPrice = priceValues.stream()
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .divide(BigDecimal.valueOf(priceValues.size()), 2, RoundingMode.HALF_UP);
        BigDecimal medianPrice = calculateMedian(priceValues);

        // Encontra loja mais barata e mais cara
        PriceHistory cheapest = latestPricesByStore.values().stream()
            .min(Comparator.comparing(PriceHistory::getPrice))
            .orElse(null);

        PriceHistory mostExpensive = latestPricesByStore.values().stream()
            .max(Comparator.comparing(PriceHistory::getPrice))
            .orElse(null);

        List<PriceComparisonDTO> comparisons = latestPricesByStore.values().stream()
            .map(this::convertToPriceComparisonDTO)
            .sorted(Comparator.comparing(PriceComparisonDTO::getPrice))
            .collect(Collectors.toList());

        PriceAnalyticsDTO result = new PriceAnalyticsDTO();
        result.setProductId(product.getId());
        result.setProductName(product.getName());
        result.setCategory(product.getCategory());
        result.setMinPrice(minPrice);
        result.setMaxPrice(maxPrice);
        result.setAveragePrice(avgPrice);
        result.setMedianPrice(medianPrice);
        result.setTotalStores(latestPricesByStore.size());
        result.setTotalRecords(prices.size());
        result.setCheapestStore(cheapest != null ? cheapest.getStore().getName() : "N/A");
        result.setMostExpensiveStore(mostExpensive != null ? mostExpensive.getStore().getName() : "N/A");
        result.setPriceRange(maxPrice.subtract(minPrice));
        result.setPricesByStore(comparisons);
        return result;
    }

    /**
     * Compara preços de um produto entre lojas de uma cidade.
     */
    public List<PriceComparisonDTO> comparePricesByCity(Long productId, String city) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        List<Store> stores = storeRepository.findActiveStoresByCity(city);

        return stores.stream()
            .flatMap(store -> priceHistoryRepository.findLatestPricesByStoreAndProduct(store, product).stream())
            .map(this::convertToPriceComparisonDTO)
            .sorted(Comparator.comparing(PriceComparisonDTO::getPrice))
            .collect(Collectors.toList());
    }

    /**
     * Compara preços de um produto entre lojas de um estado.
     */
    public List<PriceComparisonDTO> comparePricesByState(Long productId, String state) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        List<Store> stores = storeRepository.findActiveStoresByState(state);

        return stores.stream()
            .flatMap(store -> priceHistoryRepository.findLatestPricesByStoreAndProduct(store, product).stream())
            .map(this::convertToPriceComparisonDTO)
            .sorted(Comparator.comparing(PriceComparisonDTO::getPrice))
            .collect(Collectors.toList());
    }

    /**
     * Compara preços de um produto entre lojas de um país.
     */
    public List<PriceComparisonDTO> comparePricesByCountry(Long productId, String country) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        List<Store> stores = storeRepository.findActiveStoresByCountry(country);

        return stores.stream()
            .flatMap(store -> priceHistoryRepository.findLatestPricesByStoreAndProduct(store, product).stream())
            .map(this::convertToPriceComparisonDTO)
            .sorted(Comparator.comparing(PriceComparisonDTO::getPrice))
            .collect(Collectors.toList());
    }

    /**
     * Obtém histórico de preço de um produto em um período específico.
     */
    public List<PriceComparisonDTO> getPriceHistory(Long productId, LocalDateTime startDate, LocalDateTime endDate) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        return priceHistoryRepository.findPriceHistoryByProductAndDateRange(product, startDate, endDate)
            .stream()
            .map(this::convertToPriceComparisonDTO)
            .collect(Collectors.toList());
    }

    /**
     * Encontra os produtos mais baratos de uma cidade.
     */
    public List<PriceComparisonDTO> getCheapestProductsByCity(String city, int limit) {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("Cidade é obrigatória");
        }
        if (limit <= 0) {
            throw new IllegalArgumentException("Limite deve ser maior que zero");
        }

        log.info("Buscando produtos mais baratos da cidade={} com limite={}", city, limit);

        List<Store> stores = storeRepository.findActiveStoresByCity(city);
        if (stores.isEmpty()) {
            log.warn("Nenhuma loja ativa encontrada para a cidade={}", city);
            return List.of();
        }

        Map<Long, PriceHistory> latestPriceByProduct = stores.stream()
            .flatMap(store -> priceHistoryRepository.findByStore(store).stream())
            .filter(priceHistory -> priceHistory.getPrice() != null)
            .collect(Collectors.toMap(
                priceHistory -> priceHistory.getProduct().getId(),
                Function.identity(),
                (existing, replacement) -> existing.getRecordedDate().isAfter(replacement.getRecordedDate())
                    ? existing
                    : replacement
            ));

        List<PriceComparisonDTO> results = latestPriceByProduct.values().stream()
            .sorted(Comparator.comparing(PriceHistory::getPrice))
            .limit(limit)
            .map(this::convertToPriceComparisonDTO)
            .collect(Collectors.toList());

        log.info("Consulta finalizada para cidade={} retornou {} registros", city, results.size());
        return results;
    }

    /**
     * Calcula a mediana de uma lista de BigDecimal.
     */
    private BigDecimal calculateMedian(List<BigDecimal> values) {
        if (values.isEmpty()) {
            return BigDecimal.ZERO;
        }

        int size = values.size();
        if (size % 2 == 0) {
            BigDecimal middle1 = values.get(size / 2 - 1);
            BigDecimal middle2 = values.get(size / 2);
            return middle1.add(middle2)
                .divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
        } else {
            return values.get(size / 2);
        }
    }

    /**
     * Converte PriceHistory para PriceComparisonDTO.
     */
    private PriceComparisonDTO convertToPriceComparisonDTO(PriceHistory priceHistory) {
        PriceComparisonDTO dto = new PriceComparisonDTO();
        dto.setProductId(priceHistory.getProduct().getId());
        dto.setProductName(priceHistory.getProduct().getName());
        dto.setStoreId(priceHistory.getStore().getId());
        dto.setStoreName(priceHistory.getStore().getName());
        dto.setCity(priceHistory.getStore().getLocation().getCity());
        dto.setState(priceHistory.getStore().getLocation().getState());
        dto.setCountry(priceHistory.getStore().getLocation().getCountry());
        dto.setPrice(priceHistory.getPrice());
        dto.setDiscountedPrice(priceHistory.getDiscountedPrice());
        dto.setPriceType(priceHistory.getPriceType());
        dto.setRecordedDate(priceHistory.getRecordedDate());
        return dto;
    }
}
