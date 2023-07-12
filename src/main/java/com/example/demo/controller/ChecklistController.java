package com.example.demo.controller;

import com.example.demo.domain.Checklist;
import com.example.demo.domain.User;
import com.example.demo.repository.ChecklistRepository;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("/checklist")
@RestController
public class ChecklistController {

    @Autowired
    private ChecklistRepository ChecklistRepository;
    @Autowired
    private MemberRepository memberRepository;
    @GetMapping("/insert") // CREATE
    public Checklist insert(){
        User user=new User( "12345", 123131, "annie", "kim", new Date(), "한 마디");
        memberRepository.save(user);
        return ChecklistRepository.save(
                new Checklist( user, new Date(2023, 07, 12))
        );
    }
}
