package org.example.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryDTO {
    private long totalProducts;
    private long totalStores;
    private long totalReceipts;
    private BigDecimal totalSpent;
    private LocalDateTime generatedAt;
}

