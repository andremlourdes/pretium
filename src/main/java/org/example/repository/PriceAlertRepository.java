package org.example.repository;

import java.util.List;

import org.example.entity.PriceAlert;
import org.example.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceAlertRepository extends JpaRepository<PriceAlert, Long> {

    List<PriceAlert> findByActiveTrue();

    List<PriceAlert> findByProduct(Product product);
}

