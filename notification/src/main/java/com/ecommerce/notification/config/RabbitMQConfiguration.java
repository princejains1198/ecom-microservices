//package com.ecommerce.notification.config;
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMQConfiguration {
//
//    @Value("${rabbitmq.queue.name}")
//    private String queueName;
//
//    @Value("${rabbitmq.exchange.name}")
//    private String exchangeName;
//
//    @Value("${rabbitmq.routing.key}")
//    private String routingKey;
//
//    @Bean
//    public Queue getQueue() {
//        return QueueBuilder.durable(queueName)
//                .build();
//    }
//
//    @Bean
//    public TopicExchange getTopicExchange() {
//        return ExchangeBuilder.topicExchange(exchangeName)
//                .durable(true)
//                .build();
//    }
//
//    @Bean
//    public Binding getBinding() {
//        return  BindingBuilder.bind(getQueue())
//                .to(getTopicExchange())
//                .with(routingKey);
//    }
//
//    @Bean
//    public AmqpAdmin getAmqpAdmin(ConnectionFactory connectionFactory) {
//        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
//        rabbitAdmin.setAutoStartup(true);
//        return rabbitAdmin;
//    }
//
//    @Bean
//    public MessageConverter getMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
//}
