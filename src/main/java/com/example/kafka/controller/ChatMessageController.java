package com.example.kafka.controller;

import com.example.kafka.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@RestController
public class ChatMessageController {

    @Autowired
    public ChatMessageController(KafkaTemplate kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }
    @Value("${kafka.topic}")
    private String KAFKA_TOPIC;

    private final KafkaTemplate<String, ChatMessage> kafkaTemplate;

    @PostMapping(value = "/api/send", consumes = "application/json", produces = "application/json")
    public void sendMessage(@RequestBody ChatMessage message) {
        message.setSendingTime(LocalDateTime.now());
        try {
            //Sending the message to kafka topic queue
            kafkaTemplate.send(KAFKA_TOPIC, message).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/group")
    public ChatMessage broadcast(@Payload ChatMessage message){
        return message;
    }

    @MessageMapping("/newUser")
    @SendTo("/topic/group")
    public ChatMessage addUser(@Payload ChatMessage message,
                           SimpMessageHeaderAccessor headerAccessor) {
        // Add user in web socket session
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }
}
