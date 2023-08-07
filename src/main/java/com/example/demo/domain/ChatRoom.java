package com.example.demo.domain;

import com.example.demo.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="chatRoom")
@Getter
@Setter
public class ChatRoom {
    private Set<WebSocketSession> sessions = new HashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ChatRoomSession> sessions = new HashSet<>();


    @Builder
    public ChatRoom(int id) {
        this.id = id;
    }

    public void handlerActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setContent(chatMessage.getUser() + "님이 입장했습니다.");
        }
        sendMessage(chatMessage, chatService);

    }

    private <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream()
                .forEach(session -> chatService.sendMessage(session, message));
    }


    public void addChatRoomSession(WebSocketSession webSocketSession) {
        ChatRoomSession chatRoomSession = new ChatRoomSession();
        chatRoomSession.setWebSocketSession(webSocketSession);
        chatRoomSession.setChatRoom(this);
        sessions.add(chatRoomSession);
    }

    @Transient
    private Set<WebSocketSession> asessions = new HashSet<>();
}
