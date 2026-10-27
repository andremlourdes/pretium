package org.example.repository;

import org.example.entity.PriceHistory;
import org.example.entity.Product;
import org.example.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório para gerenciar entidades de Histórico de Preços.
 */
@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {

    List<PriceHistory> findByProduct(Product product);

    List<PriceHistory> findByStore(Store store);

    List<PriceHistory> findByProductAndStore(Product product, Store store);

    @Query("SELECT ph FROM PriceHistory ph WHERE ph.product = :product AND ph.recordedDate BETWEEN :startDate AND :endDate ORDER BY ph.recordedDate DESC")
    List<PriceHistory> findPriceHistoryByProductAndDateRange(@Param("product") Product product,
                                                             @Param("startDate") LocalDateTime startDate,
                                                             @Param("endDate") LocalDateTime endDate);

    @Query("SELECT ph FROM PriceHistory ph WHERE ph.store = :store AND ph.product = :product ORDER BY ph.recordedDate DESC")
    List<PriceHistory> findLatestPricesByStoreAndProduct(@Param("store") Store store,
                                                        @Param("product") Product product);

    PriceHistory findFirstByProductOrderByRecordedDateDesc(Product product);
}



