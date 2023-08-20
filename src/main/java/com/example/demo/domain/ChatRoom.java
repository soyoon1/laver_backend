package com.example.demo.domain;

import com.example.demo.service.ChatService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
//@Entity
//@Table(name="ChatRoom")
//@ToString
public class ChatRoom {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",insertable = false, updatable = false)
    private int id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="name")
    private User user;

//    @ManyToOne(targetEntity = User.class)
//    @JoinColumn(name="name")
//    private User user2;



    @Column(name = "room_name") // 컬럼 매핑 추가
    private String roomName;

//    public String getName() {
//        name=getUser().getName();
//        return name;
//    }

    @Transient
    private Set<WebSocketSession> sessions = new HashSet<>();

//    @JsonIgnore
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessage=new ArrayList<>();




    @Builder
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public void handlerActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
            sendMessage(chatMessage, chatService);
        }else if(chatMessage.getType().equals(ChatMessage.MessageType.TALK)){
            chatMessage.setMessage(chatMessage.getSender());
            sendMessage(chatMessage, chatService);
        }
    }

    private <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream()
                .forEach(session -> chatService.sendMessage(session, message));
    }

   //@OneToMany(mappedBy = "room")

    
}
