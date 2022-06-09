package com.example.kafka.controller;

import com.example.kafka.model.MessageDTO;
import com.example.kafka.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/chat")
public class KafkaController {
    private final KafkaProducer kafkaProducer;

    @Autowired
    KafkaController(KafkaProducer kafkaProducer){
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody MessageDTO message){
        try {
            log.info("SEND : " + message.toString());
            this.kafkaProducer.sendMessage(message);
            return "success";
        }
        catch(Exception e) {
            return "fail";
        }
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/group")
    public Message broadcastGroupMessage(@Payload Message message) {
        log.info("RECEIVE : " + message.toString());
        return message;
    }
}
