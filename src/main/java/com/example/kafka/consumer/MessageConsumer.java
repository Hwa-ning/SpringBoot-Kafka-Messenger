package com.example.kafka.consumer;

import com.example.kafka.model.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageConsumer {
    private static final String KAFKA_TOPIC = "quickstart-events";
    private static final String KAFKA_GROUP_ID = "foo";
    @Autowired
    SimpMessagingTemplate template;

    @KafkaListener(topics = KAFKA_TOPIC, groupId = KAFKA_GROUP_ID)
    public void listen(ChatMessage message) {
        log.info("[listen]"+message.toString());
        template.convertAndSend("/topic/group", message);
    }
}
