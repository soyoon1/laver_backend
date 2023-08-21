package com.example.demo.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int id;

    @Column(unique = true, nullable = false, length = 45)
    private String loginId;

    @Column(length=100, nullable = false)
    private String password;
    private int age;
    @Column(length=100)
    private String name;
    @Column(length=100)
    private String nickname;
    private Date birth; // @CreatedDate 를 활용할까? 아님 new Date()를 활용할까?
    @Column(length=500)
    private String sentence;

    @Column(length = 200)
    private String fcmToken;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean alarm;



    @JsonBackReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ChatRoom> chatRoom=new ArrayList<>();


    public User(String password, int age, String name, String nickname, Date date, String sentence, String fcmToken){
        this.password =password;
        this.age = age;
        this.name = name;
        this.nickname = nickname;
        this.birth = date;
        this.sentence = sentence;
        this.fcmToken = fcmToken;
    }

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

    public void addUserAuthority(){
        this.role = Role.USER;
    }

}
