package com.ecommerce.order.service;

import com.ecommerce.order.constants.OrderStatus;
import com.ecommerce.order.dto.OrderCreatedEvent;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.mapper.OrderMapper;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.model.Order;
import com.ecommerce.order.model.OrderItem;
import com.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final StreamBridge streamBridge;

//    private final RabbitTemplate rabbitTemplate;

//    @Value("${rabbitmq.exchange.name}")
//    private String exchangeName;
//
//    @Value("${rabbitmq.routing.key}")
//    private String routingKey;

    public Optional<OrderResponse> createOrder(String userId) {
        // Validate for Cart Items
        List<CartItem> cartItems = cartService.getCart(userId);
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cannot create order with empty cart");
        }

//        // Validate for User
//        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
//        if(userOptional.isEmpty()) {
//            throw new IllegalStateException("User not found");
//        }
//        User user = userOptional.get();

        // Calculate Total Amount
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create Order
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem ->  new OrderItem(
                        null,
                        cartItem.getProductId(),
                        cartItem.getQuantity(),
                        cartItem.getPrice(),
                        order
                )).toList();

        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        // Clear the Cart
        cartService.clearCart(userId);

        // publish order created event
        OrderCreatedEvent orderCreatedEvent = OrderMapper.mapToOrderCreatedEvent(savedOrder, new OrderCreatedEvent());
//        rabbitTemplate.convertAndSend(exchangeName, routingKey,
//                orderCreatedEvent);

        streamBridge.send("createOrder-out-0", orderCreatedEvent);

        // Prepare Response
        return Optional.of(OrderMapper.mapToOrderResponse(savedOrder, new OrderResponse()));
    }
}
