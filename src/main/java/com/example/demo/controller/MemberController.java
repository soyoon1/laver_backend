package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("/member")
@RestController
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/insert") // CREATE
    public User insert(){
        return memberRepository.save(
                new User( "03322311", 22, "새로운 토큰 유저", "고양이", new Date(), "한 마디 한마디", "etIElynLRyWPyoVM3hBU4w:APA91bGuFHmRN-2WR2MDlpiKT8so-8EdKi6pZ7hpbdjpHPM1hdJwkrdbrSjfvic1tQjAVWZqe-EhRkGcDuL-4GjQbDBKhooxINOKG1rHOZb7Vw2gIVAiCIW2s61Fm-YDPlox6G4lEs22")
        );
    }
}
