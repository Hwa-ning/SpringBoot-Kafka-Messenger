package com.example.kafka.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class ChatMessage{
    private String sender;
    private String senderIP;
    private String content;
    private String roomID;
    private ChatMessageType type;
    private LocalDateTime sendingTime;
}