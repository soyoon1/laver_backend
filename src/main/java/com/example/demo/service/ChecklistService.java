package com.example.demo.service;

import com.example.demo.domain.Checklist;
import com.example.demo.domain.User;
import com.example.demo.dto.ChecklistRequestDto;
import com.example.demo.dto.ChecklistResponseDto;
import com.example.demo.repository.ChecklistRepository;
import com.example.demo.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Check;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChecklistService {
    private final MemberRepository memberRepository;
    private final ChecklistRepository checklistRepository;


    //체크리스트 생성하기
    public ChecklistResponseDto createChecklist(User user, ChecklistRequestDto requestDto){
        Checklist checklist=new Checklist(user, requestDto);
        checklistRepository.save(checklist);
        return new ChecklistResponseDto(user, checklist);
    }

    //체크리스트 하나 가져오기
    public ChecklistResponseDto findOneChecklist(LocalDate date){
        Optional<Checklist> checklistOptional = checklistRepository.findByLocalDate(date);
        Checklist checklist=checklistOptional.get();
        ChecklistResponseDto checklistResponseDto=new ChecklistResponseDto(checklist);
        return checklistResponseDto;
    }



}
