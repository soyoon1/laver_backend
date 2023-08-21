package com.example.demo.service;

import com.example.demo.domain.ChatMessage;
import com.example.demo.domain.ChatRoom;
import com.example.demo.domain.User;
import com.example.demo.repository.ChatMessageRepository;
import com.example.demo.repository.ChatRoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
//@Transactional(readOnly = true)
public class ChatService {
    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;
    //private final ChatMessageRepository chatMessageRepository;
    private Map<String, ChatRoom> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoom findRoomById(int id) {
        return chatRooms.get(id);
    }

    //@Transactional
    public ChatRoom createRoom(User user, User partner) {
        int randomId = (int)Math.floor(Math.random()*100);
        ChatRoom chatRoom = ChatRoom.builder()
                .id(randomId)
                .user(user)
//                .partner(partner)
                .roomName(partner.getName() + "'s Chat")
                .build();
        chatRooms.put(String.valueOf(randomId), chatRoom);
        //chatRoomRepository.save(chatRoom);
        return chatRoom;
    }



    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
//
//package com.example.demo.service;
//
//import com.example.demo.domain.ChatMessage;
//import com.example.demo.domain.ChatRoom;
//import com.example.demo.domain.User;
//import com.example.demo.repository.ChatRoomRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.util.*;
//
//@Slf4j
//@RequiredArgsConstructor
//@Service
//@Transactional
//public class ChatService {
//    private final ObjectMapper objectMapper;
//    private final ChatRoomRepository chatRoomRepository;
//    private Map<String, ChatRoom> chatRooms;
//
//    @PostConstruct
//    private void init() {
//        chatRooms = new LinkedHashMap<>();
//    }
//
//    public List<ChatRoom> findAllRoom() {
//        return new ArrayList<>(chatRooms.values());
//    }
//
//    public ChatRoom findRoomById(int id) {
//        return chatRooms.get(id);
//    }
//
////    public void addWebSocketSession(String roomId, WebSocketSession session) {
////        if (chatRooms.containsKey(roomId)) {
////            ChatRoom chatRoom = chatRooms.get(roomId);
////            chatRoom.getSessions().add(session);
////        }
////    }
////
////    public void removeWebSocketSession(String roomId, WebSocketSession session) {
////        if (chatRooms.containsKey(roomId)) {
////            ChatRoom chatRoom = chatRooms.get(roomId);
////            chatRoom.getSessions().remove(session);
////        }
////    }
//
//    public ChatRoom createRoom(User user, User partner) {
//        int randomId = (int)Math.floor(Math.random()*100);
//        ChatRoom chatRoom = ChatRoom.builder()
//                .id(randomId)
//                .user(user)
//                .roomName(partner.getName() + "'s Chat")
//                .build();
//        chatRooms.put(String.valueOf(randomId), chatRoom);
//        //addWebSocketSession(String.valueOf(randomId), (WebSocketSession) chatRoom.getSessions());
//        //chatRoomRepository.save(chatRoom);
//        return chatRoom;
//    }
//
//    public <T> void sendMessage(WebSocketSession session, T message) {
//        try {
//            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//
//
//
//}
