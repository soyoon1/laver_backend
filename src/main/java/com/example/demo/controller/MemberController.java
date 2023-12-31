package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("/api/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

    // 테스트할 때 쓰는 코드
    private final MemberRepository memberRepository;

    @GetMapping("/insert") // CREATE
    public User insert(){
        return memberRepository.save(
                new User("user", "03322311", 22, "만들어진 유저", "고양이", new Date(), "한 마디 한마디", "ddUZlXa6TemNBlhJB_v-85:APA91bF0x7amNi08LJmz5hcrAXzrtDWqQFpmpTpXrHVlETxgkmswFVYEY-qk7k0PzkXgoiuoq8K-ZIsSmFV994OfLdOwmGCVAwdOttBgSZPJeA6GjTeWKHnrYEw_fSYwng8NqlQiPTcZ")
        );
    }
}