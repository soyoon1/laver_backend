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
    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("{}", payload);

        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        log.info("chatroom =====> "+chatMessage);
        log.info("chatroom =====> "+chatMessage.getMessage());
        log.info("chatroom =====> "+chatMessage.getType());
        //log.info("chatroom =====> "+chatMessage.getChatRoom());
        if (chatMessage.getUser() != null) {
            log.info("chatroom =====> "+chatMessage.getUser().getId());
        } else {
            log.info("chatroom =====> User is null");
        }


        ChatRoom chatRoom = chatService.findRoomById(chatMessage.getChatRoom().getId());
        log.debug("chatroom"+chatRoom);
        chatRoom.handlerActions(session, chatMessage, chatService);
    }

}