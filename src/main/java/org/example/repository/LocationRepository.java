package org.example.repository;

import org.example.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para gerenciar entidades de Localização.
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByCountryAndStateAndCity(String country, String state, String city);

    List<Location> findByCountry(String country);

    List<Location> findByCountryAndState(String country, String state);

    List<Location> findByCity(String city);
}



