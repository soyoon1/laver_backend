package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
public class User {
    @Id @GeneratedValue
    @Column(name="user_id")
    private int id;

    @Column(length=100)
    private String password;
    private int age;
    @Column(length=100)
    private String name;
    @Column(length=100)
    private String nickname;
    private Date birth;
    @Column(length=500)
    private String sentence;

    public User(String password, int age, String name, String nickname, Date date, String sentence){
        this.password =password;
        this.age = age;
        this.name = name;
        this.nickname = nickname;
        this.birth = date;
        this.sentence = sentence;
    }

}
