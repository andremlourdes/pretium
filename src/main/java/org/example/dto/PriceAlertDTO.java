package org.example.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceAlertDTO {
    private Long id;
    private Long productId;
    private String productName;
    private BigDecimal targetPrice;
    private String conditionType;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime lastNotifiedAt;
}

