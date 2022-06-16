package com.example.kafka.controller;

import com.example.kafka.model.ChatMessage;
import lombok.extern.slf4j.Slf4j;
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

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@Slf4j
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
    public void sendMessage(@RequestBody ChatMessage message, HttpServletRequest request) {
        message.setSendingTime(LocalDateTime.now());
        message.setSenderIP(request.getRemoteAddr());
        try {
            //Sending the message to kafka topic queue

            kafkaTemplate.send(KAFKA_TOPIC, message).get();
           // kafkaTemplate.send(KAFKA_TOPIC + message.getRoomId(), message).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/group")
    public ChatMessage broadcast(@Payload ChatMessage message){
        log.info("[broadCast] : " + message);
        return message;
    }

    @MessageMapping("/newUser")
    @SendTo("/topic/group")
    public ChatMessage addUser(@Payload ChatMessage message,
                           SimpMessageHeaderAccessor headerAccessor) {
        // Add user in web socket session
        log.info("[IN]" + message.getSender());
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }
}
