package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa uma localização geográfica.
 * Armazena informações de país, estado e cidade.
 */
@Entity
@Table(name = "locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(length = 20)
    private String zipCode;

    @Column(nullable = false, columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double latitude;

    @Column(nullable = false, columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double longitude;
}



