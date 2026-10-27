package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * Entidade que representa um item em um cupom de compra.
 */
@Entity
@Table(name = "purchase_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receipt_id", nullable = false)
    private Receipt receipt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 1")
    private Integer quantity;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal unitPrice;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal totalPrice;

    @Column(columnDefinition = "DECIMAL(10,2) DEFAULT 0.0")
    private BigDecimal discountAmount;

    @Column(columnDefinition = "DECIMAL(10,2) DEFAULT 0.0")
    private BigDecimal taxAmount;

    @Column(length = 20)
    private String itemNumber;
}



