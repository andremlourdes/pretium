package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para transferência de dados de Cupom de Compra.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDTO {

    private Long id;

    private Long storeId;

    private String storeName;

    private String receiptNumber;

    private LocalDateTime purchaseDate;

    private BigDecimal totalAmount;

    private BigDecimal discountAmount;

    private BigDecimal taxAmount;

    private String paymentMethod;

    private String notes;

    private String status;

    private List<PurchaseItemDTO> items;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}



