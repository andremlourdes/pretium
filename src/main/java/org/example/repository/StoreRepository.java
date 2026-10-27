package org.example.repository;

import org.example.entity.Store;
import org.example.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para gerenciar entidades de Supermercados.
 */
@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByName(String name);

    List<Store> findByLocation(Location location);

    List<Store> findByStatus(String status);

    @Query("SELECT s FROM Store s WHERE s.location.city = :city AND s.status = 'ACTIVE'")
    List<Store> findActiveStoresByCity(@Param("city") String city);

    @Query("SELECT s FROM Store s WHERE s.location.state = :state AND s.status = 'ACTIVE'")
    List<Store> findActiveStoresByState(@Param("state") String state);

    @Query("SELECT s FROM Store s WHERE s.location.country = :country AND s.status = 'ACTIVE'")
    List<Store> findActiveStoresByCountry(@Param("country") String country);
}



