package com.app.ecom.service;

import com.app.ecom.constants.OrderStatus;
import com.app.ecom.dto.OrderResponse;
import com.app.ecom.mapper.OrderMapper;
import com.app.ecom.model.CartItem;
import com.app.ecom.model.Order;
import com.app.ecom.model.OrderItem;
import com.app.ecom.model.User;
import com.app.ecom.repository.OrderRepository;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartService cartService;

    public Optional<OrderResponse> createOrder(String userId) {
        // Validate for Cart Items
        List<CartItem> cartItems = cartService.getCart(userId);
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cannot create order with empty cart");
        }

        // Validate for User
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if(userOptional.isEmpty()) {
            throw new IllegalStateException("User not found");
        }
        User user = userOptional.get();

        // Calculate Total Amount
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create Order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem ->  new OrderItem(
                        null,
                        cartItem.getProduct(),
                        cartItem.getQuantity(),
                        cartItem.getPrice(),
                        order
                )).toList();

        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        // Clear the Cart
        cartService.clearCart(userId);

        // Prepare Response
        return Optional.of(OrderMapper.mapToOrderResponse(savedOrder, new OrderResponse()));
    }
}
