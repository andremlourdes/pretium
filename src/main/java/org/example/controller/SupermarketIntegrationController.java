package org.example.controller;

import java.util.List;

import org.example.dto.SupermarketProductDTO;
import org.example.integration.SupermarketProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/integrations/supermarkets")
public class SupermarketIntegrationController {

    private final SupermarketProvider provider;

    public SupermarketIntegrationController(SupermarketProvider provider) {
        this.provider = provider;
    }

    @GetMapping("/products")
    public ResponseEntity<List<SupermarketProductDTO>> search(@RequestParam String query) {
        return ResponseEntity.ok(provider.search(query));
    }
}

