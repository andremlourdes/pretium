package org.example.controller;

import org.example.dto.PriceForecastDTO;
import org.example.service.PricePredictionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/predictions")
public class PredictionController {

    private final PricePredictionService pricePredictionService;

    public PredictionController(PricePredictionService pricePredictionService) {
        this.pricePredictionService = pricePredictionService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<PriceForecastDTO> forecast(@PathVariable Long productId) {
        return ResponseEntity.ok(pricePredictionService.forecast(productId));
    }
}

