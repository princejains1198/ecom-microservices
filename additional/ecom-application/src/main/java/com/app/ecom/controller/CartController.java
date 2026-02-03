package com.app.ecom.controller;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.model.CartItem;
import com.app.ecom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId,
                                          @RequestBody CartItemRequest request) {
        if(!(cartService.addToCart(userId, request))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product Out of Stock or User not found or Product not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Product Added Successfully");
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(@RequestHeader("X-User-ID") String userId,@PathVariable Long productId) {
        Boolean deletedItemFromCart = cartService.deleteItemFromCart(userId, productId);
        if(deletedItemFromCart) {
            return status(HttpStatus.NO_CONTENT).build();
        }
        return status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping
    public ResponseEntity<CartItem> getCart(@RequestHeader("X-User-ID") String userId) {
        List<CartItem> cartItems = cartService.getCart(userId);
        if (!cartItems.isEmpty() && cartItems != null) {
            return ResponseEntity.status(HttpStatus.OK).body(cartItems.get(0));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
