package org.example;

import org.example.entity.Location;
import org.example.entity.Product;
import org.example.entity.Store;
import org.example.repository.LocationRepository;
import org.example.repository.ProductRepository;
import org.example.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class IntegrationSmokeTest {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Test
    void shouldPersistLocationProductAndStore() {
        Location location = new Location();
        location.setCountry("Brasil");
        location.setState("São Paulo");
        location.setCity("São Paulo");
        location.setZipCode("01000-000");
        location.setLatitude(-23.55);
        location.setLongitude(-46.63);
        locationRepository.save(location);

        Product product = new Product();
        product.setName("Leite Integral");
        product.setBarcode("1234567890123");
        product.setCategory("Laticínios");
        product.setBrand("Marca A");
        product.setUnit("L");
        product.setWeight(1.0);
        product.setDescription("Leite integral de teste");
        product.setStatus("ACTIVE");
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);

        Store store = new Store();
        store.setName("Mercado Teste");
        store.setDescription("Loja de teste");
        store.setPhone("11999999999");
        store.setWebsite("https://teste.com");
        store.setLocation(location);
        store.setStatus("ACTIVE");
        store.setCreatedAt(LocalDateTime.now());
        store.setUpdatedAt(LocalDateTime.now());
        storeRepository.save(store);

        assertThat(locationRepository.count()).isEqualTo(1);
        assertThat(productRepository.count()).isEqualTo(1);
        assertThat(storeRepository.count()).isEqualTo(1);
    }
}

