package com.example.kafka.repository;

import com.example.kafka.model.ChatRoom;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ChatRoomRepository {
    private final Map<String, ChatRoom> chatRooms = new HashMap<>();

    public List<ChatRoom> findAllRoom(){
        return new ArrayList<>(chatRooms.values());
    }

    public Optional<ChatRoom> findRoomById(String id){
        return Optional.ofNullable(chatRooms.get(id));
    }
    public Optional<ChatRoom> createRoom(ChatRoom chatRoom){
        chatRooms.put(chatRoom.getRoomID(),chatRoom);
        return Optional.of(chatRoom);
    }
}
