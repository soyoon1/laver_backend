package com.example.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int id;

    @Column(length=100)
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

//    @OneToMany(mappedBy = "user") // 양방향 관계 매핑된 것. 읽기 전용
//    private List<Medication> medications = new ArrayList<>();

    public User(String password, int age, String name, String nickname, Date date, String sentence, String fcmToken){
        this.password =password;
        this.age = age;
        this.name = name;
        this.nickname = nickname;
        this.birth = date;
        this.sentence = sentence;
        this.fcmToken = fcmToken;
    }

}
