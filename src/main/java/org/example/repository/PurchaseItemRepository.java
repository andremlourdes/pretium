package org.example.repository;

import org.example.entity.PurchaseItem;
import org.example.entity.Receipt;
import org.example.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositório para gerenciar entidades de Itens de Compra.
 */
@Repository
public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {

    List<PurchaseItem> findByReceipt(Receipt receipt);

    List<PurchaseItem> findByProduct(Product product);

    @Query("SELECT pi FROM PurchaseItem pi WHERE pi.receipt = :receipt AND pi.product = :product")
    List<PurchaseItem> findByReceiptAndProduct(@Param("receipt") Receipt receipt,
                                               @Param("product") Product product);
}



