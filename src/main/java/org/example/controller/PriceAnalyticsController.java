package org.example.controller;

import org.example.dto.PriceAnalyticsDTO;
import org.example.dto.PriceComparisonDTO;
import org.example.service.PriceAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller REST para análise e comparação de preços.
 *
 * Endpoints:
 * - GET /api/prices/analysis/{productId} - Análise completa de preços de um produto
 * - GET /api/prices/compare/city/{productId}/{city} - Comparar preços por cidade
 * - GET /api/prices/compare/state/{productId}/{state} - Comparar preços por estado
 * - GET /api/prices/compare/country/{productId}/{country} - Comparar preços por país
 * - GET /api/prices/history/{productId} - Histórico de preços
 */
@RestController
@RequestMapping("/api/prices")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PriceAnalyticsController {

    @Autowired
    private PriceAnalyticsService priceAnalyticsService;

    /**
     * Obtém análise completa de preços de um produto.
     *
     * @param productId ID do produto
     * @return Análise com min, máx, média, mediana e preços por loja
     */
    @GetMapping("/analysis/{productId}")
    public ResponseEntity<PriceAnalyticsDTO> analyzePrices(@PathVariable Long productId) {
        try {
            PriceAnalyticsDTO analysis = priceAnalyticsService.analyzeProductPrice(productId);
            return ResponseEntity.ok(analysis);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
        }
    }

    /**
     * Compara preços de um produto entre lojas de uma cidade.
     *
     * @param productId ID do produto
     * @param city Nome da cidade
     * @return Lista de preços comparados, ordenada do mais barato para o mais caro
     */
    @GetMapping("/compare/city/{productId}/{city}")
    public ResponseEntity<List<PriceComparisonDTO>> comparePricesByCity(
            @PathVariable Long productId,
            @PathVariable String city) {
        try {
            List<PriceComparisonDTO> comparisons = priceAnalyticsService.comparePricesByCity(productId, city);
            return ResponseEntity.ok(comparisons);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
        }
    }

    /**
     * Compara preços de um produto entre lojas de um estado.
     *
     * @param productId ID do produto
     * @param state Nome do estado
     * @return Lista de preços comparados
     */
    @GetMapping("/compare/state/{productId}/{state}")
    public ResponseEntity<List<PriceComparisonDTO>> comparePricesByState(
            @PathVariable Long productId,
            @PathVariable String state) {
        try {
            List<PriceComparisonDTO> comparisons = priceAnalyticsService.comparePricesByState(productId, state);
            return ResponseEntity.ok(comparisons);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
        }
    }

    /**
     * Compara preços de um produto entre lojas de um país.
     *
     * @param productId ID do produto
     * @param country Nome do país
     * @return Lista de preços comparados
     */
    @GetMapping("/compare/country/{productId}/{country}")
    public ResponseEntity<List<PriceComparisonDTO>> comparePricesByCountry(
            @PathVariable Long productId,
            @PathVariable String country) {
        try {
            List<PriceComparisonDTO> comparisons = priceAnalyticsService.comparePricesByCountry(productId, country);
            return ResponseEntity.ok(comparisons);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
        }
    }

    /**
     * Obtém o histórico de preços de um produto em um período específico.
     *
     * @param productId ID do produto
     * @param startDate Data de início (formato: yyyy-MM-ddTHH:mm:ss)
     * @param endDate Data de fim (formato: yyyy-MM-ddTHH:mm:ss)
     * @return Lista de preços históricos
     */
    @GetMapping("/history/{productId}")
    public ResponseEntity<List<PriceComparisonDTO>> getPriceHistory(
            @PathVariable Long productId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            List<PriceComparisonDTO> history = priceAnalyticsService.getPriceHistory(productId, startDate, endDate);
            return ResponseEntity.ok(history);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
        }
    }
}



