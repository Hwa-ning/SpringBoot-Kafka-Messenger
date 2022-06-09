package com.example.kafka.service;

import com.example.kafka.model.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProducer {
    private static final String TOPIC = "quickstart-events";
    private final KafkaTemplate <String, MessageDTO> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessage(MessageDTO message){
        log.info(message.toString());
        this.kafkaTemplate.send(TOPIC,message);
    }
}
