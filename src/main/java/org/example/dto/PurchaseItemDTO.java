package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * DTO para transferência de dados de Item de Compra.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseItemDTO {

    private Long id;

    private Long receiptId;

    private Long productId;

    private String productName;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    private BigDecimal discountAmount;

    private BigDecimal taxAmount;

    private String itemNumber;
}



