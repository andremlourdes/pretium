package org.example.controller;

import java.math.BigDecimal;
import java.util.List;

import org.example.dto.PriceAlertDTO;
import org.example.service.PriceAlertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alerts")
public class PriceAlertController {

    private final PriceAlertService priceAlertService;

    public PriceAlertController(PriceAlertService priceAlertService) {
        this.priceAlertService = priceAlertService;
    }

    @PostMapping
    public ResponseEntity<PriceAlertDTO> create(@RequestParam Long productId,
                                                @RequestParam BigDecimal targetPrice,
                                                @RequestParam(defaultValue = "BELOW") String conditionType) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(priceAlertService.create(productId, targetPrice, conditionType));
    }

    @GetMapping
    public ResponseEntity<List<PriceAlertDTO>> findAll() {
        return ResponseEntity.ok(priceAlertService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceAlertDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(priceAlertService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        priceAlertService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}

