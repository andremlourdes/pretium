package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para transferência de dados de Localização.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {

    private Long id;

    private String country;

    private String state;

    private String city;

    private String zipCode;

    private Double latitude;

    private Double longitude;
}



