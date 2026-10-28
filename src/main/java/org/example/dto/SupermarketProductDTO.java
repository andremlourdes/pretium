package org.example.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupermarketProductDTO {
    private String externalId;
    private String name;
    private BigDecimal price;
    private String source;
    private String productUrl;
}

