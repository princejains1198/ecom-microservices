package com.ecommerce.product.controller;

import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        // Implementation for adding a product
        return new ResponseEntity<>(productService.createProduct(productRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        // Implementation for getting all products
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest updateProductRequest) {
        // Implementation for updating a product
        return productService.updateProduct(id, updateProductRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long id) {
        // Implementation for deleting a product
        boolean result = productService.deleteProduct(id);
        return result ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyword) {
        // Implementation for getting a product by ID
        List<ProductResponse> products = productService.searchProducts(keyword);
        return products.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id) {
        // Implementation for getting a product by ID
        ProductResponse productResponse = productService.searchProductsById(id);
        return productResponse != null ? ResponseEntity.ok(productResponse) : ResponseEntity.notFound().build();
    }

    @GetMapping("/simulate")
    public ResponseEntity<String> simulateFailures(@RequestParam(defaultValue = "false") boolean fail) {
        // Implementation for simulating failures
        if(fail) {
            throw new RuntimeException("Simulate Failure For Testing");
        }
        return new ResponseEntity<>("Product Service is OK", HttpStatus.OK);
    }
}
