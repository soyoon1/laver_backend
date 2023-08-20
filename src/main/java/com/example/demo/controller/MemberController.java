package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

    //@Autowired
    private final MemberRepository memberRepository;

 //autowired 만들지 말고 위에 @RequiredArgsConstructor를 위해 적어주기
//단 private final로 선언해주었을 때만 가능
//setter말고 생성자 취급으로 해주기

    @GetMapping("/insert") // CREATE
    public User insert(){
        return memberRepository.save(
                new User( "03322311", 22, "또또 새로운 토큰 유저", "고양이", new Date(), "한 마디 한마디", "fgDHZUPpRwWYA6uD8jLG5d:APA91bHhz_kbghqZyEHlOVncWZjlhUPjilOtryMa4pBJINdKiPMro3lHdXCA07-YHRha8aaeJ3LTmz0cwSp11Mzzf2SB1XVt3dVJsVXoQgvYjBORIrKKAJKexg_2BlfTQKDHtIxJ3BuY")
        );
    }
}
