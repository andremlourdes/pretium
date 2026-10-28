package org.example;

import org.example.entity.Product;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PretiumSmokeTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void contextLoads() {
        assertThat(productRepository).isNotNull();
    }

    @Test
    void canPersistProduct() {
        Product product = new Product();
        product.setName("Arroz 5kg");
        product.setBarcode("7891234567890");
        product.setCategory("Alimentos");
        product.setBrand("Marca Teste");
        product.setUnit("KG");
        product.setWeight(5.0);
        product.setDescription("Produto de teste");
        product.setStatus("ACTIVE");
        product.setCreatedAt(java.time.LocalDateTime.now());
        product.setUpdatedAt(java.time.LocalDateTime.now());

        Product saved = productRepository.save(product);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Arroz 5kg");
    }
}

