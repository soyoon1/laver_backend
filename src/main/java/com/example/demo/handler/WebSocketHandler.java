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
//

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
        if (objectMapper == null) {
            log.error("ObjectMapper is null");
        }

        if (chatService == null) {
            log.error("ChatService is null");
        }
        if (session == null) {
            log.error("ChatService is null");
        }
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);

        ChatRoom chatRoom = chatService.findRoomById(chatMessage.getRoomId());
        log.debug("chatroom"+chatRoom);
        chatRoom.handlerActions(session, chatMessage, chatService);
    }
}
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class WebSocketHandler extends TextWebSocketHandler {
//    private final ObjectMapper objectMapper;
//    private final ChatService chatService;
//    //전체 세션
//    //private static List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        log.info("{}", payload);
//        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
//
//        ChatRoom chatRoom = chatService.findRoomById(chatMessage.getChatRoom());
//        chatRoom.handlerActions(session, chatMessage, chatService);
//    }

    //연결이 종료됐을 때
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        //전체 세션리스트에서 세션 삭제
//        sessions.remove(session);
//    }

//    window.onbeforeunload=function(event) {
//        WS.onclose = function() {
//        }
//        ;
//        WS.close();
//    }
//}

//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class WebSocketHandler extends TextWebSocketHandler {
//    private final ObjectMapper objectMapper;
//    private final ChatService chatService;
//    private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//
//        log.info("#ChattingHandler, afterConnectionEstablished");
//        sessionList.add(session);
//
//        log.info(session.getPrincipal().getName() + "님이 입장하셨습니다.");
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//
//        log.info("#ChattingHandler, handleMessage");
//        log.info(session.getId() + ": " + message);
//
//        for(WebSocketSession s : sessionList) {
//            s.sendMessage(new TextMessage(session.getPrincipal().getName() + ":" + message.getPayload()));
//        }
//    }
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//
//        log.info("#ChattingHandler, afterConnectionClosed");
//
//        sessionList.remove(session);
//
//        log.info(session.getPrincipal().getName() + "님이 퇴장하셨습니다.");
//    }
//}