package com.example.demo.handler;
import com.example.demo.domain.ChatMessage;
import com.example.demo.domain.ChatRoom;
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

import static java.awt.SystemColor.window;


@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private static List<WebSocketSession> list = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : " + payload);

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