package org.example.controller;

import org.example.dto.StoreDTO;
import org.example.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciar Supermercados.
 */
@RestController
@RequestMapping("/api/stores")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StoreController {

    @Autowired
    private StoreService storeService;

    /**
     * Cria um novo supermercado.
     */
    @PostMapping("/{locationId}")
    public ResponseEntity<StoreDTO> createStore(
            @PathVariable Long locationId,
            @RequestBody StoreDTO storeDTO) {
        try {
            StoreDTO created = storeService.createStore(locationId, storeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Obtém um supermercado por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StoreDTO> getStoreById(@PathVariable Long id) {
        try {
            StoreDTO store = storeService.getStoreById(id);
            return ResponseEntity.ok(store);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Lista todos os supermercados.
     */
    @GetMapping
    public ResponseEntity<List<StoreDTO>> getAllStores() {
        List<StoreDTO> stores = storeService.getAllStores();
        return ResponseEntity.ok(stores);
    }

    /**
     * Obtém supermercados por nome.
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<List<StoreDTO>> getStoresByName(@PathVariable String name) {
        List<StoreDTO> stores = storeService.getStoresByName(name);
        return ResponseEntity.ok(stores);
    }

    /**
     * Obtém supermercados por localização (ID da localização).
     */
    @GetMapping("/location/{locationId}")
    public ResponseEntity<List<StoreDTO>> getStoresByLocation(@PathVariable Long locationId) {
        try {
            List<StoreDTO> stores = storeService.getStoresByLocation(locationId);
            return ResponseEntity.ok(stores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Obtém supermercados por cidade.
     */
    @GetMapping("/city/{city}")
    public ResponseEntity<List<StoreDTO>> getStoresByCity(@PathVariable String city) {
        List<StoreDTO> stores = storeService.getStoresByCity(city);
        return ResponseEntity.ok(stores);
    }

    /**
     * Obtém supermercados por estado.
     */
    @GetMapping("/state/{state}")
    public ResponseEntity<List<StoreDTO>> getStoresByState(@PathVariable String state) {
        List<StoreDTO> stores = storeService.getStoresByState(state);
        return ResponseEntity.ok(stores);
    }

    /**
     * Obtém supermercados por país.
     */
    @GetMapping("/country/{country}")
    public ResponseEntity<List<StoreDTO>> getStoresByCountry(@PathVariable String country) {
        List<StoreDTO> stores = storeService.getStoresByCountry(country);
        return ResponseEntity.ok(stores);
    }

    /**
     * Obtém supermercados por status.
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<StoreDTO>> getStoresByStatus(@PathVariable String status) {
        List<StoreDTO> stores = storeService.getStoresByStatus(status);
        return ResponseEntity.ok(stores);
    }

    /**
     * Atualiza um supermercado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StoreDTO> updateStore(
            @PathVariable Long id,
            @RequestBody StoreDTO storeDTO) {
        try {
            StoreDTO updated = storeService.updateStore(id, storeDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Ativa um supermercado.
     */
    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activateStore(@PathVariable Long id) {
        try {
            storeService.activateStore(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Desativa um supermercado.
     */
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateStore(@PathVariable Long id) {
        try {
            storeService.deactivateStore(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Deleta um supermercado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        try {
            storeService.deleteStore(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}



