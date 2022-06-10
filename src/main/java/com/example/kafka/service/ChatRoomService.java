package com.example.kafka.service;

import com.example.kafka.model.ChatRoom;
import com.example.kafka.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChatRoomService {
    @Autowired
    public ChatRoomService(ChatRoomRepository chatRoomRepository){
        this.chatRoomRepository = chatRoomRepository;
    }
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoom createChatRoom(String roomName){
        ChatRoom chatRoom = ChatRoom.builder()
                .roomID(UUID.randomUUID().toString())
                .name(roomName)
                .build();

        return chatRoomRepository.createRoom(chatRoom)
                .orElseThrow(RuntimeException::new);
    }

    public ChatRoom findRoomById(String roomID){
        return chatRoomRepository.findRoomById(roomID)
                .orElseThrow(RuntimeException::new);
    }
    public List<ChatRoom> findAllRoom(){
        List<ChatRoom> chatRooms = chatRoomRepository.findAllRoom();

        if(chatRooms.size() == 0)
            throw new RuntimeException();

        return chatRooms;
    }
}
