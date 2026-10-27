package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO para transferência de dados de Supermercado.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreDTO {

    private Long id;

    private String name;

    private String description;

    private String phone;

    private String website;

    private LocationDTO location;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}



