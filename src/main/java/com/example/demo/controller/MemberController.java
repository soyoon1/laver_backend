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
                new User( "090879", 123, "whthdbs", "wesdfsdfsfsgdgdg", new Date(), "한 마디")
        );
    }
}
