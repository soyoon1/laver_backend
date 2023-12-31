package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Getter
@Setter
//@Table(name="ChatMessage")
//@NotNull
//@NotEmpty
//@NotBlank
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
    @JsonIgnore
    @JoinColumn(name="room_id")
    private ChatRoom chatRoom;



    //private int userId;


    @ManyToOne(targetEntity = User.class)
    @JsonManagedReference
    @JsonIgnore
    @JoinColumn(name="user_id")
    private User user;



    //@Column(length = 100)
    @NotBlank(message = "Message cannot be blank")
    private String message;

}