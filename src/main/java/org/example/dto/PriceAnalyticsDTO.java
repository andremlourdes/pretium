package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para análise de preços de um produto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceAnalyticsDTO {

    private Long productId;

    private String productName;

    private String category;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private BigDecimal averagePrice;

    private BigDecimal medianPrice;

    private Integer totalStores;

    private Integer totalRecords;

    private String cheapestStore;

    private String mostExpensiveStore;

    private BigDecimal priceRange;

    private List<PriceComparisonDTO> pricesByStore;
}



