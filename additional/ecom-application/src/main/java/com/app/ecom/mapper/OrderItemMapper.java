package com.app.ecom.mapper;

import com.app.ecom.dto.OrderItemDto;
import com.app.ecom.model.OrderItem;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OrderItemMapper {

    public static OrderItemDto mapToOrderItemDto(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        BigDecimal totalPrice = orderItem.getPrice()
                .multiply(BigDecimal.valueOf(orderItem.getQuantity()));
        return new OrderItemDto(
                orderItem.getId(),
                orderItem.getProduct().getId(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                totalPrice
        );
    }

    public static List<OrderItemDto> mapToOrderItemDtosList(List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            return Collections.emptyList();
        }
        return items.stream()
                .map(OrderItemMapper::mapToOrderItemDto)
                .collect(Collectors.toList());
    }
}
