package com.cabd.cabd.controller;

import com.cabd.cabd.dao.model.Product;
import com.cabd.cabd.dao.model.ProductHistory;
import com.cabd.cabd.service.ProductHistoryService;
import com.cabd.cabd.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/productHistories")
@CrossOrigin
@RequiredArgsConstructor
public class ProductHistoryController {
    private final ProductService productService;
    private final ProductHistoryService productHistoryService;

    @GetMapping
    public List<ProductHistory> getAllProductsHistories() {
        return productHistoryService.getAllProductsHistories();
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productService.updateProduct(id, updatedProduct);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/{id}")
    public Optional<Product> getCurrentState(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/state-at-timestamp")
    public ResponseEntity<Map<String, Object>> getStateAtTimestamp(
            @RequestParam Long productId,
            @RequestParam String timestamp) {
        try {
            LocalDateTime parsedTimestamp = LocalDateTime.parse(timestamp);
            Map<String, Object> state = productHistoryService.getStateAtTimestamp(productId, parsedTimestamp);
            return ResponseEntity.ok(state);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid timestamp format"));
        }
    }

    @GetMapping("/price-periods/{productId}")
    public ResponseEntity<Map<String, Object>> getPricePeriods(@PathVariable Long productId) {
        return ResponseEntity.ok(productHistoryService.getPricePeriods(productId));
    }

    @GetMapping("/price-differences/{productId}")
    public ResponseEntity<List<Map<String, Object>>> getPriceDifferences(@PathVariable Long productId) {
        return ResponseEntity.ok(productHistoryService.getPriceDifferences(productId));
    }
}
