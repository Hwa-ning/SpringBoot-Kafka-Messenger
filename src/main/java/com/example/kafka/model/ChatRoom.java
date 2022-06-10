package com.example.kafka.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ChatRoom {
    private String roomID;
    private String name;
    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();
}
