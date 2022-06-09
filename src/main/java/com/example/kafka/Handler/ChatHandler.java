package com.example.kafka.Handler;

import com.example.kafka.model.MessageDTO;
import com.example.kafka.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ChatHandler extends TextWebSocketHandler {
    private static List<WebSocketSession> webSocketSessionList = new ArrayList<>();
    private final KafkaProducer kafkaProducer;

    @Autowired
    public ChatHandler(KafkaProducer kafkaProducer){
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{

        MessageDTO newMessage = MessageDTO.builder()
                .content(message.getPayload())
                .build();

        log.info(newMessage.toString());

        kafkaProducer.sendMessage(newMessage);

        for(WebSocketSession wss : webSocketSessionList){
            if(wss.getId() != session.getId()) {
                wss.sendMessage(message);
            }
        }
    }
    /* Client Connected */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info(session + " 클라이언트 접속");
        webSocketSessionList.add(session);
    }

    /* Client Disconnected */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        log.info(session + " 클라이언트 접속 해제");
        webSocketSessionList.remove(session);
    }
}
