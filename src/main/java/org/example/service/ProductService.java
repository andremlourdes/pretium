package org.example.service;

import org.example.dto.ProductDTO;
import org.example.entity.Product;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar operações com Produtos.
 */
@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setBarcode(productDTO.getBarcode());
        product.setCategory(productDTO.getCategory());
        product.setBrand(productDTO.getBrand());
        product.setUnit(productDTO.getUnit());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        product.setStatus("ACTIVE");
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Product saved = productRepository.save(product);
        return convertToDTO(saved);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        product.setName(productDTO.getName());
        product.setBarcode(productDTO.getBarcode());
        product.setCategory(productDTO.getCategory());
        product.setBrand(productDTO.getBrand());
        product.setUnit(productDTO.getUnit());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        product.setUpdatedAt(LocalDateTime.now());

        Product updated = productRepository.save(product);
        return convertToDTO(updated);
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return convertToDTO(product);
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getProductByName(String name) {
        return productRepository.findByName(name)
            .map(this::convertToDTO);
    }

    public Optional<ProductDTO> getProductByBarcode(String barcode) {
        return productRepository.findByBarcode(barcode)
            .map(this::convertToDTO);
    }

    public List<ProductDTO> getProductsByCategory(String category) {
        return productRepository.findByCategory(category).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByStatus(String status) {
        return productRepository.findByStatus(status).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setBarcode(product.getBarcode());
        dto.setCategory(product.getCategory());
        dto.setBrand(product.getBrand());
        dto.setUnit(product.getUnit());
        dto.setWeight(product.getWeight());
        dto.setDescription(product.getDescription());
        dto.setStatus(product.getStatus());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        return dto;
    }
}




