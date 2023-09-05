package com.example.demo.domain;

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

    @Temporal(TemporalType.DATE) // 2021-03-24 이렇게 데이터를 저장할 수 있도록 하기 위함.
    private Date createdDate; // 복용 일수를 계산하기 위함.

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY) // 양방향 관계 매핑된 것. 읽기 전용
    private List<Medication> medications = new ArrayList<>();

    public User(String loginId, String password, int age, String name, String nickname, Date date, String sentence, String fcmToken){
        this.loginId = loginId;
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
