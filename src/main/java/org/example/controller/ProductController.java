package org.example.controller;

import org.example.dto.ProductDTO;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para gerenciar Produtos.
 */
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Cria um novo produto.
     */
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            ProductDTO created = productService.createProduct(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Obtém um produto por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        try {
            ProductDTO product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Lista todos os produtos.
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Obtém um produto por nome.
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<ProductDTO> getProductByName(@PathVariable String name) {
        Optional<ProductDTO> product = productService.getProductByName(name);
        return product.map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    /**
     * Obtém um produto por código de barras.
     */
    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<ProductDTO> getProductByBarcode(@PathVariable String barcode) {
        Optional<ProductDTO> product = productService.getProductByBarcode(barcode);
        return product.map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    /**
     * Obtém produtos por categoria.
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable String category) {
        List<ProductDTO> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    /**
     * Obtém produtos por marca.
     */
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<ProductDTO>> getProductsByBrand(@PathVariable String brand) {
        List<ProductDTO> products = productService.getProductsByBrand(brand);
        return ResponseEntity.ok(products);
    }

    /**
     * Obtém produtos por status.
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProductDTO>> getProductsByStatus(@PathVariable String status) {
        List<ProductDTO> products = productService.getProductsByStatus(status);
        return ResponseEntity.ok(products);
    }

    /**
     * Atualiza um produto.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO updated = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Deleta um produto.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}



