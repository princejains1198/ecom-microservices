package com.app.ecom.service;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.model.CartItem;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartItemRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public Boolean addToCart(String userId, CartItemRequest cartItemRequest) {
        // Look for the product and user, create a CartItem entity, and save it
        // Implementation details would go here
        Optional<Product> productOpt = productRepository.findById(cartItemRequest.getProductId());
        if (productOpt.isEmpty()) {
            return false;
        }

        Product product = productOpt.get();
        if (product.getStockQuantity() < cartItemRequest.getQuantity()) {
            return false;
        }

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();

        CartItem exitingCartItem = cartItemRepository.findByUserAndProduct(user, product);

        if (exitingCartItem != null) {
            //Updating existing cart item quantity
            exitingCartItem.setQuantity(exitingCartItem.getQuantity() + cartItemRequest.getQuantity());
            exitingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(exitingCartItem.getQuantity())));
            cartItemRepository.save(exitingCartItem);
        } else {
            //Creating new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(
                    java.math.BigDecimal.valueOf(cartItemRequest.getQuantity())
            ));
            cartItemRepository.save(cartItem);
        }
        return true;
    }


    public Boolean deleteItemFromCart(String userId, Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (productOpt.isPresent() && userOpt.isPresent()) {
            cartItemRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
            return true;
        }
        return false;
    }

    public List<CartItem> getCart(String userId) {
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isPresent()) {
            return cartItemRepository.findByUser(userOpt.get());
        }
        return List.of();
    }

    public void clearCart(String userId) {
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isPresent()) {
            cartItemRepository.deleteByUser(userOpt.get());
        }
    }
}