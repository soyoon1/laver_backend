package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="chatMessage")
@Getter
@Setter
public class ChatMessage {
    @Id
    @GeneratedValue
    @Column(name="message_id")
    private int messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;




}
