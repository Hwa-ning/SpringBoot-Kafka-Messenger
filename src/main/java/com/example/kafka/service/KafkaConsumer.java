package com.example.kafka.service;

import com.example.kafka.model.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class KafkaConsumer {
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public void KafkaConsumer(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @KafkaListener(topics="quickstart-events", groupId ="foo")
    public void consume(MessageDTO message) throws IOException{
        log.info("Consumer : " + message.toString());
        simpMessagingTemplate.convertAndSend("/topic/group",message);
    }
}
