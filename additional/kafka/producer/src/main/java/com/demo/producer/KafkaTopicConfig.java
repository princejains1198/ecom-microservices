package com.demo.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic newTopic() {
        return new NewTopic("my-new-topic-2", 3, (short) 1);
    }
}
