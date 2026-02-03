package com.ecommerce.order.controller;

import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.service.CartService;
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not able to complete the request");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Product Added Successfully");
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(@RequestHeader("X-User-ID") String userId,@PathVariable String productId) {
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
