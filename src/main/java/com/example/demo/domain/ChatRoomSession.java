//package com.example.demo.domain;
//
//import org.springframework.web.socket.WebSocketSession;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Entity
//@Table(name = "chatRoomSession")
//public class ChatRoomSession {
//
//    private WebSocketSession webSocketSession;
//    @Id
//    @GeneratedValue
//    @Column(name = "session_id")
//    private int sessionId;
//
//    @Column(name = "web_socket_session_id")
//    private String webSocketSessionId; // Store WebSocket session ID or relevant identifier
//
//    // ... other fields and annotations
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "room_id")
//    private ChatRoom chatRoom;
//
//
//
//    public void setChatRoom(ChatRoom chatRoom) {
//        this.chatRoom = chatRoom;
//    }
//
//    public void setWebSocketSession(WebSocketSession webSocketSession) {
//        this.webSocketSession = webSocketSession;
//    }
//}