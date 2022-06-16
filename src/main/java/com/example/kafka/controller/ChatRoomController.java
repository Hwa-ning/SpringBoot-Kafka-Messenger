package com.example.kafka.controller;

import com.example.kafka.model.ChatRoom;
import com.example.kafka.service.ChatRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatRoomController {
    private final SimpMessageSendingOperations simpMessageTemplate;
    private final ChatRoomService chatRoomService;

    public ChatRoomController(SimpMessageSendingOperations simpMessageTemplate,ChatRoomService chatRoomService){
        this.simpMessageTemplate = simpMessageTemplate;
        this.chatRoomService = chatRoomService;
    }

    @PostMapping("/api/room")
    public ResponseEntity<?> createChatRoom(@RequestBody String name)
    {
        ChatRoom createdChatRoom = chatRoomService.createChatRoom(name);

        return ResponseEntity.ok(createdChatRoom);
    }

    @GetMapping("/api/room")
    public ResponseEntity<?> createChatRoom()
    {
        List<ChatRoom> chatRooms = chatRoomService.findAllRoom();

        return ResponseEntity.ok(chatRooms);
    }
}