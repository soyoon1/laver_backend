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
                new User("soyoon", "03322311", 22, "또또 새로운 토큰 유저", "고양이", new Date(), "한 마디 한마디", "ddUZlXa6TemNBlhJB_v-85:APA91bF0x7amNi08LJmz5hcrAXzrtDWqQFpmpTpXrHVlETxgkmswFVYEY-qk7k0PzkXgoiuoq8K-ZIsSmFV994OfLdOwmGCVAwdOttBgSZPJeA6GjTeWKHnrYEw_fSYwng8NqlQiPTcZ")
        );
    }
}