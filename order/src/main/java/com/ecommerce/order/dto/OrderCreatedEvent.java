package com.ecommerce.order.dto;

import com.ecommerce.order.constants.OrderStatus;
import com.ecommerce.order.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {

    private Long orderId;
    private String userId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private List<OrderItemDto> orderItems;
    private LocalDateTime createdAt;

}
