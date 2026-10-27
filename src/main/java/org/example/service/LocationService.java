package org.example.service;

import org.example.dto.LocationDTO;
import org.example.entity.Location;
import org.example.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar operações com Localizações.
 */
@Service
@Transactional
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public LocationDTO createLocation(LocationDTO locationDTO) {
        Location location = new Location();
        location.setCountry(locationDTO.getCountry());
        location.setState(locationDTO.getState());
        location.setCity(locationDTO.getCity());
        location.setZipCode(locationDTO.getZipCode());
        location.setLatitude(locationDTO.getLatitude());
        location.setLongitude(locationDTO.getLongitude());

        Location saved = locationRepository.save(location);
        return convertToDTO(saved);
    }

    public LocationDTO updateLocation(Long id, LocationDTO locationDTO) {
        Location location = locationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Localização não encontrada"));

        location.setCountry(locationDTO.getCountry());
        location.setState(locationDTO.getState());
        location.setCity(locationDTO.getCity());
        location.setZipCode(locationDTO.getZipCode());
        location.setLatitude(locationDTO.getLatitude());
        location.setLongitude(locationDTO.getLongitude());

        Location updated = locationRepository.save(location);
        return convertToDTO(updated);
    }

    public LocationDTO getLocationById(Long id) {
        Location location = locationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Localização não encontrada"));
        return convertToDTO(location);
    }

    public List<LocationDTO> getAllLocations() {
        return locationRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<LocationDTO> getLocationsByCountry(String country) {
        return locationRepository.findByCountry(country).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<LocationDTO> getLocationsByCountryAndState(String country, String state) {
        return locationRepository.findByCountryAndState(country, state).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public Optional<LocationDTO> getLocationByCountryStateCity(String country, String state, String city) {
        return locationRepository.findByCountryAndStateAndCity(country, state, city)
            .map(this::convertToDTO);
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }

    private LocationDTO convertToDTO(Location location) {
        LocationDTO dto = new LocationDTO();
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




