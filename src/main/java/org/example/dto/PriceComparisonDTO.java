package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para comparação de preços de um produto entre lojas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceComparisonDTO {

    private Long productId;

    private String productName;

    private Long storeId;

    private String storeName;

    private String city;

    private String state;

    private String country;

    private BigDecimal price;

    private BigDecimal discountedPrice;

    private String priceType;

    private LocalDateTime recordedDate;

    private BigDecimal priceChange;

    private Double priceChangePercentage;
}



