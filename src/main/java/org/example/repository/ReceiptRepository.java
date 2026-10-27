package org.example.repository;

import org.example.entity.Receipt;
import org.example.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para gerenciar entidades de Cupons.
 */
@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    Optional<Receipt> findByReceiptNumber(String receiptNumber);

    List<Receipt> findByStore(Store store);

    List<Receipt> findByStatus(String status);

    @Query("SELECT r FROM Receipt r WHERE r.store = :store AND r.purchaseDate BETWEEN :startDate AND :endDate")
    List<Receipt> findReceiptsByStoreAndDateRange(@Param("store") Store store,
                                                   @Param("startDate") LocalDateTime startDate,
                                                   @Param("endDate") LocalDateTime endDate);

    @Query("SELECT r FROM Receipt r WHERE r.purchaseDate BETWEEN :startDate AND :endDate ORDER BY r.purchaseDate DESC")
    List<Receipt> findReceiptsByDateRange(@Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);

    @Query("SELECT r FROM Receipt r WHERE r.store.location.city = :city AND r.purchaseDate BETWEEN :startDate AND :endDate")
    List<Receipt> findReceiptsByCityAndDateRange(@Param("city") String city,
                                                 @Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate);
}



