package org.example.service;

import org.example.dto.StoreDTO;
import org.example.entity.Store;
import org.example.entity.Location;
import org.example.repository.StoreRepository;
import org.example.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar operações com Supermercados.
 */
@Service
@Transactional
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private LocationRepository locationRepository;

    public StoreDTO createStore(Long locationId, StoreDTO storeDTO) {
        Location location = locationRepository.findById(locationId)
            .orElseThrow(() -> new RuntimeException("Localização não encontrada"));

        Store store = new Store();
        store.setName(storeDTO.getName());
        store.setDescription(storeDTO.getDescription());
        store.setPhone(storeDTO.getPhone());
        store.setWebsite(storeDTO.getWebsite());
        store.setLocation(location);
        store.setStatus("ACTIVE");
        store.setCreatedAt(LocalDateTime.now());
        store.setUpdatedAt(LocalDateTime.now());

        Store saved = storeRepository.save(store);
        return convertToDTO(saved);
    }

    public StoreDTO updateStore(Long id, StoreDTO storeDTO) {
        Store store = storeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Supermercado não encontrado"));

        store.setName(storeDTO.getName());
        store.setDescription(storeDTO.getDescription());
        store.setPhone(storeDTO.getPhone());
        store.setWebsite(storeDTO.getWebsite());
        store.setUpdatedAt(LocalDateTime.now());

        Store updated = storeRepository.save(store);
        return convertToDTO(updated);
    }

    public StoreDTO getStoreById(Long id) {
        Store store = storeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Supermercado não encontrado"));
        return convertToDTO(store);
    }

    public List<StoreDTO> getAllStores() {
        return storeRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<StoreDTO> getStoresByName(String name) {
        return storeRepository.findByName(name).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<StoreDTO> getStoresByLocation(Long locationId) {
        Location location = locationRepository.findById(locationId)
            .orElseThrow(() -> new RuntimeException("Localização não encontrada"));
        return storeRepository.findByLocation(location).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<StoreDTO> getStoresByCity(String city) {
        return storeRepository.findActiveStoresByCity(city).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<StoreDTO> getStoresByState(String state) {
        return storeRepository.findActiveStoresByState(state).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<StoreDTO> getStoresByCountry(String country) {
        return storeRepository.findActiveStoresByCountry(country).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<StoreDTO> getStoresByStatus(String status) {
        return storeRepository.findByStatus(status).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }

    public void activateStore(Long id) {
        Store store = storeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Supermercado não encontrado"));
        store.setStatus("ACTIVE");
        store.setUpdatedAt(LocalDateTime.now());
        storeRepository.save(store);
    }

    public void deactivateStore(Long id) {
        Store store = storeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Supermercado não encontrado"));
        store.setStatus("INACTIVE");
        store.setUpdatedAt(LocalDateTime.now());
        storeRepository.save(store);
    }

    private StoreDTO convertToDTO(Store store) {
        StoreDTO dto = new StoreDTO();
        dto.setId(store.getId());
        dto.setName(store.getName());
        dto.setDescription(store.getDescription());
        dto.setPhone(store.getPhone());
        dto.setWebsite(store.getWebsite());
        dto.setLocation(convertLocationToDTO(store.getLocation()));
        dto.setStatus(store.getStatus());
        dto.setCreatedAt(store.getCreatedAt());
        dto.setUpdatedAt(store.getUpdatedAt());
        return dto;
    }

    private org.example.dto.LocationDTO convertLocationToDTO(Location location) {
        org.example.dto.LocationDTO dto = new org.example.dto.LocationDTO();
        dto.setId(location.getId());
        dto.setCountry(location.getCountry());
        dto.setState(location.getState());
        dto.setCity(location.getCity());
        dto.setZipCode(location.getZipCode());
        dto.setLatitude(location.getLatitude());
        dto.setLongitude(location.getLongitude());
        return dto;
    }
}




