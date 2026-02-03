package com.ecommerce.notification.service;

import com.ecommerce.notification.payload.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Slf4j
public class OrderEventConsumer {

    /*@RabbitListener(queues = "${rabbitmq.queue.name}")
    public void handleOrderEvent(OrderCreatedEvent orderEvent) {
        System.out.println("Received Order Event: " + orderEvent);

        long orderId = orderEvent.getOrderId();
        OrderStatus orderStatus = orderEvent.getStatus();


        System.out.println("Order ID: " + orderId);
        System.out.println("Order Status: " + orderStatus);
    }*/

    @Bean
    public Consumer<OrderCreatedEvent> orderCreated() {
        return orderEvent -> {
            log.info("Received Order Created Event for Order: {}", orderEvent.getOrderId());
            log.info("Received Order Created Event for User Id: {}", orderEvent.getUserId());
        };
    }
}
