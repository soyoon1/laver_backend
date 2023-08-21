package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

                new User( "03322311", 22, "sjsdjkdjkf", "고양이", new Date(), "한 마디 한마디", "fgDHZUPpRwWYA6uD8jLG5d:APA91bHhz_kbghqZyEHlOVncWZjlhUPjilOtryMa4pBJINdKiPMro3lHdXCA07-YHRha8aaeJ3LTmz0cwSp11Mzzf2SB1XVt3dVJsVXoQgvYjBORIrKKAJKexg_2BlfTQKDHtIxJ3BuY")

        );
    }
}