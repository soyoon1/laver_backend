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
                new User( "03322311", 22, "또또 새로운 토큰 유저", "고양이", new Date(), "한 마디 한마디", "fO7Rd3VARZWYQTirwMTsUv:APA91bF2izQfj5_n8t_GtfQwr5yAMq6mg9fcnctY4FsX_26B81_RVvB0JxfKTJiIKXPV_91iUSUFNYgn--124VxUt27GNactfLOcdzwiwWIf-slmEfe3SCb8l_DNPJwlNSIv3ql3dBKU")
        );
    }
}
