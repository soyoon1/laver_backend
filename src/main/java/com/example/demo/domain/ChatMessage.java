package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;



@Entity
@Getter
@Setter
//@Table(name="ChatMessage")
@NotNull
@NotEmpty
@NotBlank
//@AllArgsConstructor
public class ChatMessage {
    public enum MessageType{
        ENTER, TALK
    }

    private MessageType type;

    //private String roomId;
    //private String sender;
    @Id
    @GeneratedValue
    @Column(name="message_id")
    private int messageid;

    @ManyToOne(targetEntity = ChatRoom.class)
    @JsonManagedReference
    @JoinColumn(name="id")
    private ChatRoom chatRoom;


//
    //private int userId;


//    @ManyToOne(targetEntity = User.class)
//    @JoinColumn(name="id")
//    private User user;

    //@Column(length = 100)
    private String message;

}
