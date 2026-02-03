package com.ecommerce.order.service;

import com.ecommerce.order.clients.ProductServiceClient;
import com.ecommerce.order.clients.UserServiceClient;
import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.dto.ProductResponse;
import com.ecommerce.order.dto.UserResponse;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.repository.CartItemRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartItemRepository cartItemRepository;

    private final ProductServiceClient productServiceClient;

    private final UserServiceClient userServiceClient;

    int attempt = 0;

//    @CircuitBreaker(name = "productService", fallbackMethod = "addToCartFallBack")
    @Retry(name = "retryBreaker", fallbackMethod = "addToCartFallBack")
    public Boolean addToCart(String userId, CartItemRequest cartItemRequest) {
        // Look for the product and user, create a CartItem entity, and save it
        // Implementation details would go here

        System.out.println("Attempt Count: " + attempt++);
        ProductResponse productResponse = productServiceClient.getProductDetails(cartItemRequest.getProductId());
        if (productResponse == null || productResponse.getStockQuantity() < cartItemRequest.getQuantity()) {
            return false;
        }

        UserResponse userResponse = userServiceClient.getUserDetails(userId);
        if (userResponse == null) {
            return false;
        }

        CartItem exitingCartItem = cartItemRepository.findByUserIdAndProductId(userId, cartItemRequest.getProductId());

        if (exitingCartItem != null) {
            //Updating existing cart item quantity
            exitingCartItem.setQuantity(exitingCartItem.getQuantity() + cartItemRequest.getQuantity());
            exitingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(exitingCartItem);
        } else {
            //Creating new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(cartItemRequest.getProductId());
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public Boolean addToCartFallBack(String userId, CartItemRequest cartItemRequest, Exception exception) {
        System.out.println("Fallback method called for addToCart");
        exception.printStackTrace();
        return false;
    }

    public Boolean deleteItemFromCart(String userId, String productId) {
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
            return true;
        }
        return false;
    }

    public List<CartItem> getCart(String userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        if (cartItems != null) {
            return cartItems;
        }
        return List.of();
    }

    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}