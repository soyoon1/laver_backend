package com.example.demo.handler;
import com.example.demo.domain.ChatMessage;
import com.example.demo.domain.ChatRoom;
import com.example.demo.domain.User;
import com.example.demo.repository.ChatMessageRepository;
import com.example.demo.repository.ChatRoomRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import org.springframework.web.socket.CloseStatus;


import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.awt.SystemColor.window;


@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private static List<WebSocketSession> list = new ArrayList<>();
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
//    private final ChatRoom chatRoom = ChatRoomRepository.findById(chatRoomId).orElse(null);
//    private fianl User user = UserRepository.findById(userId).orElse(null);


//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        log.info("payload : " + payload);
//        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
//
//
//        // 메시지 필드에 유효한 값을 할당
//        chatMessage.setMessage(payload);
//        chatMessage.setType(ChatMessage.MessageType.TALK);
//
//        // 메시지를 데이터베이스에 저장
//        chatMessageRepository.save(chatMessage);
//
//        // 모든 연결된 클라이언트에게 메시지를 전송
//        for (WebSocketSession sess : list) {
//            sess.sendMessage(message);
//        }
//    }

//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        log.info("payload : " + payload);
//        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
//
//        // ChatRoom과 User를 데이터베이스에서 조회
//        ChatRoom chatRoom = chatRoomRepository.findById(chatMessage.getChatRoom().getId());
//        User user = userRepository.findById(Long.valueOf(chatMessage.getUser().getId()));
//
//        // 조회한 객체를 chatMessage에 설정
//        chatMessage.setChatRoom(chatRoom);
//        chatMessage.setUser(user);
//
//        // 메시지를 데이터베이스에 저장
//        chatMessageRepository.save(chatMessage);
//
//        // 모든 연결된 클라이언트에게 메시지를 전송
//        for (WebSocketSession sess : list) {
//            sess.sendMessage(message);
//        }
//    }
//protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//    String payload = message.getPayload();
//    log.info("payload : " + payload);
//    ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
//
//    // ChatRoom을 데이터베이스에서 조회하고 기본값으로 Optional.empty()를 사용
//    Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(chatMessage.getChatRoom().getId());
//    ChatRoom yourChatRoomObject = chatRoomOptional.orElse(null);
//
//    // User를 데이터베이스에서 조회하고 기본값으로 Optional.empty()를 사용
//    Optional<User> userOptional = userRepository.findById((long) chatMessage.getUser().getId());
//    User yourUserObject = userOptional.orElse(null);
//
//    // 조회한 객체를 chatMessage에 설정
//    chatMessage.setChatRoom(yourChatRoomObject);
//    chatMessage.setUser(yourUserObject);
//
//    // 메시지를 데이터베이스에 저장
//    chatMessageRepository.save(chatMessage);
//
//    // 모든 연결된 클라이언트에게 메시지를 전송
//    for (WebSocketSession sess : list) {
//        sess.sendMessage(message);
//    }
//}

//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        log.info("payload : " + payload);
//        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
//
//        // ChatRoom을 데이터베이스에서 조회하고 기본값으로 Optional.empty()를 사용
//        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(chatMessage.getChatRoom().getId());
//        ChatRoom yourChatRoomObject = chatRoom.orElse(null);
//
//        // User를 데이터베이스에서 조회하고 기본값으로 Optional.empty()를 사용
//        Optional<User> userOptional = userRepository.findById((long) chatMessage.getUser().getId());
//        User yourUserObject = userOptional.orElse(null);
//
//        // 조회한 객체를 chatMessage에 설정
//        chatMessage.setChatRoom(yourChatRoomObject);
//        chatMessage.setUser(yourUserObject);
//
//        // 메시지를 데이터베이스에 저장
//        chatMessageRepository.save(chatMessage);
//
//        // 모든 연결된 클라이언트에게 메시지를 전송
//        for (WebSocketSession sess : list) {
//            sess.sendMessage(message);
//        }
////    }

//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        log.info("payload : " + payload);
//        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
//
//        // ChatRoom을 데이터베이스에서 조회하고 기본값으로 Optional.empty()를 사용
//        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(chatMessage.getChatRoom().getId());
//        if (chatRoomOptional.isPresent()) {
//            ChatRoom yourChatRoomObject = chatRoomOptional.get();
//
//            // User를 데이터베이스에서 조회하고 기본값으로 Optional.empty()를 사용
//            Optional<User> userOptional = userRepository.findById((long) chatMessage.getUser().getId());
//            if (userOptional.isPresent()) {
//                User yourUserObject = userOptional.get();
//
//                // 조회한 객체를 chatMessage에 설정
//                chatMessage.setChatRoom(yourChatRoomObject);
//                chatMessage.setUser(yourUserObject);
//
//                // 메시지를 데이터베이스에 저장
//                chatMessageRepository.save(chatMessage);
//
//                // 모든 연결된 클라이언트에게 메시지를 전송
//                for (WebSocketSession sess : list) {
//                    sess.sendMessage(message);
//                }
//            } else {
//                // User가 존재하지 않는 경우 처리
//                log.error("User not found");
//            }
//        } else {
//            // ChatRoom이 존재하지 않는 경우 처리
//            log.error("ChatRoom not found");
//        }
//    }

//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        log.info("payload : " + payload);
//        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
//
//        // 메시지 필드에 유효한 값을 할당
//        //chatMessage.setMessage(payload);
//
//        chatMessageRepository.save(chatMessage);
//
//        for(WebSocketSession sess: list) {
//            sess.sendMessage(message);
//        }
//    }

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : " + payload);
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);

        // JSON에서 "message" 필드의 값을 추출하여 chatMessage의 message 필드에 할당
        chatMessage.setMessage(chatMessage.getMessage());

        // 메시지를 데이터베이스에 저장
        chatMessageRepository.save(chatMessage);

        for(WebSocketSession sess: list) {
            sess.sendMessage(message);
        }
    }

    /* Client가 접속 시 호출되는 메서드 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        list.add(session);

        log.info(session + " 클라이언트 접속");
    }

    /* Client가 접속 해제 시 호출되는 메서드드 */

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        log.info(session + " 클라이언트 접속 해제");
        list.remove(session);
    }
//    private final ObjectMapper objectMapper;
//    private final ChatService chatService;
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        log.info("{}", payload);
//
//        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
//        log.info("chatroom =====> "+chatMessage);
//        log.info("chatroom =====> "+chatMessage.getMessage());
//        log.info("chatroom =====> "+chatMessage.getChatRoom().getId());
//        log.info("chatroom =====> "+chatMessage.getType());
//
//        if (chatMessage.getUser() != null) {
//            log.info("chatroom =====> "+chatMessage.getUser().getId());
//        } else {
//            log.info("chatroom =====> User is null");
//        }
//
//
//        ChatRoom chatRoom = chatService.findRoomById(chatMessage.getChatRoom().getId());
//        log.debug("chatroom"+chatRoom);
//        chatRoom.handlerActions(session, chatMessage, chatService);
//    }

}