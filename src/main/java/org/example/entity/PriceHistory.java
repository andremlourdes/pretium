package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade que rastreia o histórico de preços de um produto em uma loja.
 */
@Entity
@Table(name = "price_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal price;

    @Column(columnDefinition = "DECIMAL(10,2) DEFAULT 0.0")
    private BigDecimal discountedPrice;

    @Column(columnDefinition = "VARCHAR(50) DEFAULT 'REGULAR'")
    private String priceType; // REGULAR, PROMOTION, SALE

    @Column(length = 500)
    private String promotionDescription;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime recordedDate;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
}



