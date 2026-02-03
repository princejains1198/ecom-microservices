package com.ecommerce.gateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class FallbackController {

    @GetMapping("/fallback/products")
    public ResponseEntity<List<String>> productsFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonList("Product Service is unavailable, please try after some time"));
    }

    @GetMapping("/fallback/users")
    public ResponseEntity<List<String>> usersFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonList("Users Service is unavailable, please try after some time"));
    }

    @GetMapping("/fallback/orders")
    public ResponseEntity<List<String>> ordersFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonList("Orders Service is unavailable, please try after some time"));
    }

    @GetMapping("/fallback/cart")
    public ResponseEntity<List<String>> cartFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonList("Carts Service is unavailable, please try after some time"));
    }
}
