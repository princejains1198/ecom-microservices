package com.demo.producer;

//import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//public class KafkaProducer {
//
//    private final KafkaTemplate<String, RiderLocation> kafkaTemplate;
//
//    @PostMapping("/send")
//    public String sendMessage(@RequestParam String message){
//        RiderLocation location = new RiderLocation("rider123", 28.61, 77.23);
//        kafkaTemplate.send("my-topic-new", location);
//        return "Message Sent: " + location.getRiderId();
//    }
//}
