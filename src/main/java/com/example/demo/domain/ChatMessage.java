package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;



@Entity
@Getter
@Setter
@Table(name="ChatMessage")
public class ChatMessage {
    public enum MessageType{
        ENTER, TALK
    }

    private MessageType type;

    @Id
    @GeneratedValue
    @Column(name="message_id")
    private String messageid;

    @ManyToOne(targetEntity = ChatRoom.class)
    @JoinColumn(name="roomId")
    private ChatRoom chatRoom;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="id")
    private User user;

    @Column(length = 100)
    private String message;

    private LocalDateTime timestamp;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
