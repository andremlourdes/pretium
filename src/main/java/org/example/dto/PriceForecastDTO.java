package org.example.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceForecastDTO {
    private Long productId;
    private String productName;
    private BigDecimal currentPrice;
    private BigDecimal predictedPrice;
    private String trend;
    private Integer recordsUsed;
    private LocalDateTime generatedAt;
}

