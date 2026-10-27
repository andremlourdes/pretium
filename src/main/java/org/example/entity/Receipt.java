package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

/**
 * Entidade que representa um cupom de compra.
 */
@Entity
@Table(name = "receipts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false, unique = true, length = 50)
    private String receiptNumber;

    @Column(nullable = false)
    private LocalDateTime purchaseDate;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2) DEFAULT 0.0")
    private BigDecimal totalAmount;

    @Column(columnDefinition = "DECIMAL(10,2) DEFAULT 0.0")
    private BigDecimal discountAmount;

    @Column(columnDefinition = "DECIMAL(10,2) DEFAULT 0.0")
    private BigDecimal taxAmount;

    @Column(length = 100)
    private String paymentMethod;

    @Column(length = 500)
    private String notes;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PurchaseItem> items;

    @Column(nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'ACTIVE'")
    private String status; // ACTIVE, CANCELLED, ARCHIVED

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;
}



