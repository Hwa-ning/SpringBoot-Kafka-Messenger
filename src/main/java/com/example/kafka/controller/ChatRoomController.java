package com.example.kafka.controller;

import com.example.kafka.model.ChatRoom;
import com.example.kafka.service.ChatRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatRoomController {
    private final SimpMessageSendingOperations simpMessageTemplate;
    private final ChatRoomService chatRoomService;

    public ChatRoomController(SimpMessageSendingOperations simpMessageTemplate,ChatRoomService chatRoomService){
        this.simpMessageTemplate = simpMessageTemplate;
        this.chatRoomService = chatRoomService;
    }

    @PostMapping("/room/create")
    public ResponseEntity<?> createChatRoom(@RequestBody String name)
    {
        ChatRoom createdChatRoom = chatRoomService.createChatRoom(name);

        return ResponseEntity.ok(createdChatRoom);
    }
}
