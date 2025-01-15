package com.cabd.cabd.controller;

import com.cabd.cabd.dao.model.Product;
import com.cabd.cabd.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
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
            Map<String, Object> state = productService.getStateAtTimestamp(productId, parsedTimestamp);
            return ResponseEntity.ok(state);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid timestamp format"));
        }
    }

    @GetMapping("/price-periods/{productId}")
    public ResponseEntity<String> getPricePeriods(@PathVariable Long productId) {
//        System.out.println("In controller, cake id:" + productId);
//        List<Product> products = productService.find(cakeId);
//
//        String result = cakeHistoryService.calculatePricePeriods(priceRecords);
//
//        return ResponseEntity.ok(result.toString());
        return null;
    }

    @GetMapping("/price-differences/{cakeId}")
    public ResponseEntity<String> getPriceDifferences(@PathVariable Long cakeId) {
//        List<org.json.JSONObject> differences = cakeHistoryService.getPriceDifferences(cakeId);
//        return ResponseEntity.ok(differences.toString());
        return null;
    }
}
