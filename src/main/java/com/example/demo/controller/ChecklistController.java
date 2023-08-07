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
        User user=new User( "12345", 123131, "annie", "kim", new Date(), "한 마디", "eZwsW8KKQZGVFiZAFj0RwB:APA91bHXTrK28tbVKFAten4iFTY8hz16x8qCB8l5rymIlkSCCuEuu5rvxnOIh5HQ7EwYzWD4q4a2-M-PpUsMXJbtK8QTm-b_2kPLDFta4MSRwUBnVo_R4qCD7JpKgHS09GA5hr8B9txM");
        memberRepository.save(user);
        return ChecklistRepository.save(
                new Checklist( user, new Date(123, 6, 12)) // year은 1900 + n으로 처리, month는 n + 1로 처리   최종적으로 2023-07-12로 처리됨.
        );
    }
}
