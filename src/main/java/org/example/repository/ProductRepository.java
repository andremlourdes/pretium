package org.example.repository;

import org.example.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para gerenciar entidades de Produtos.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);

    Optional<Product> findByBarcode(String barcode);

    List<Product> findByCategory(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByStatus(String status);

    List<Product> findByCategoryAndStatus(String category, String status);
}



