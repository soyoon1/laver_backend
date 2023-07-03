package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="chatRoom")
@Getter
@Setter
public class ChatRoom {
    @Id
    @GeneratedValue
    @Column(name="room_id")
    private int room_number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;


}
