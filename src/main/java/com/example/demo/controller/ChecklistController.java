package com.example.demo.controller;

import com.example.demo.domain.Checklist;
import com.example.demo.domain.User;
import com.example.demo.dto.ChecklistRequestDto;
import com.example.demo.dto.ChecklistResponseDto;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.ChecklistRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.ChecklistService;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
//@RequestMapping("/api/cheklist")
@RestController
public class ChecklistController {


    private final ChecklistService checklistService;
    private final MemberRepository memberRepository;

    public ChecklistController(ChecklistService checklistService, MemberRepository memberRepository){
        this.checklistService=checklistService;
        this.memberRepository=memberRepository;
    }

    @PostMapping("/api/checklist")
    public ChecklistResponseDto createChecklist(@RequestBody ChecklistRequestDto requestDto){
        User user = memberRepository.findById(JwtUtil.getCurrentMemberId()).orElseThrow(()-> new RuntimeException("로그인 유저 정보가 없습니다."));
        return checklistService.createChecklist(user, requestDto);
    }

    @GetMapping("/api/checklist/{date}")
    public ChecklistResponseDto getOneChecklist(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        return checklistService.findOneChecklist(date);
    }

}
