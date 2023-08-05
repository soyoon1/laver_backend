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
                new User( "03322311", 22, "또 새로운 토큰 유저", "고양이", new Date(), "한 마디 한마디", "eV0BlQKaThKxlAk28Y4yHb:APA91bEC0ECiuHHeWAE8ZfNhA9sRvr6TYJ9lXmLsn_4rwebeNknBCLLHRyyE1vHq7JKi5oeoem8UEp4pFQG_4omu2f7XNczVZ-_PHZW2K3ZQ3hLpsgAKgigxqYg7TxzEwnOdxYNSf4uD")
        );
    }
}
