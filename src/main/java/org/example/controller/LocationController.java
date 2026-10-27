package org.example.controller;

import org.example.dto.LocationDTO;
import org.example.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para gerenciar Localizações.
 */
@RestController
@RequestMapping("/api/locations")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LocationController {

    @Autowired
    private LocationService locationService;

    /**
     * Cria uma nova localização.
     */
    @PostMapping
    public ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO locationDTO) {
        try {
            LocationDTO created = locationService.createLocation(locationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Obtém uma localização por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getLocationById(@PathVariable Long id) {
        try {
            LocationDTO location = locationService.getLocationById(id);
            return ResponseEntity.ok(location);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Obtém todas as localizações.
     */
    @GetMapping
    public ResponseEntity<List<LocationDTO>> getAllLocations() {
        List<LocationDTO> locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations);
    }

    /**
     * Obtém localizações por país.
     */
    @GetMapping("/country/{country}")
    public ResponseEntity<List<LocationDTO>> getLocationsByCountry(@PathVariable String country) {
        List<LocationDTO> locations = locationService.getLocationsByCountry(country);
        return ResponseEntity.ok(locations);
    }

    /**
     * Obtém localizações por país e estado.
     */
    @GetMapping("/country/{country}/state/{state}")
    public ResponseEntity<List<LocationDTO>> getLocationsByCountryAndState(
            @PathVariable String country,
            @PathVariable String state) {
        List<LocationDTO> locations = locationService.getLocationsByCountryAndState(country, state);
        return ResponseEntity.ok(locations);
    }

    /**
     * Obtém uma localização específica por país, estado e cidade.
     */
    @GetMapping("/country/{country}/state/{state}/city/{city}")
    public ResponseEntity<LocationDTO> getLocationByCountryStateCity(
            @PathVariable String country,
            @PathVariable String state,
            @PathVariable String city) {
        Optional<LocationDTO> location = locationService.getLocationByCountryStateCity(country, state, city);
        return location.map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    /**
     * Atualiza uma localização.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LocationDTO> updateLocation(
            @PathVariable Long id,
            @RequestBody LocationDTO locationDTO) {
        try {
            LocationDTO updated = locationService.updateLocation(id, locationDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Deleta uma localização.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        try {
            locationService.deleteLocation(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}



