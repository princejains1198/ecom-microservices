package com.app.ecom.mapper;

import com.app.ecom.dto.OrderItemDto;
import com.app.ecom.dto.OrderResponse;
import com.app.ecom.model.Order;

public class OrderMapper {

    public static OrderResponse mapToOrderResponse(Order order, OrderResponse orderResponse) {
        orderResponse.setId(order.getId());
        orderResponse.setTotalAmount(order.getTotalAmount());
        orderResponse.setOrderStatus(order.getStatus());
//        orderResponse.setOrderItems(order.getOrderItems().stream()
//                .map(orderItem -> new OrderItemDto(
//                        orderItem.getId(),
//                        orderItem.getProduct().getId(),
//                        orderItem.getQuantity(),
//                        orderItem.getPrice(),
//                        orderItem.getPrice().multiply(java.math.BigDecimal.valueOf(orderItem.getQuantity())
//                ))).toList());
        orderResponse.setOrderItems(OrderItemMapper.mapToOrderItemDtosList(order.getOrderItems()));
        orderResponse.setCreatedAt(order.getCreatedAt());
        return orderResponse;
    }
}
