package com.example.demo.dto;

import com.example.demo.domain.Checklist;
import com.example.demo.domain.User;

import java.time.LocalDate;

public class ChecklistResponseDto {
    private boolean q1;
    private boolean q2;
    private String q3;
    private LocalDate localDate;

    public ChecklistResponseDto(User user, Checklist checklist){
        this.q1=checklist.isQ1();
        this.q2= checklist.isQ2();
        this.q3= checklist.getQ3();
        this.localDate=checklist.getCreatedAt().toLocalDate();
    }

    public ChecklistResponseDto(Checklist checklist){
        this.q1=checklist.isQ1();
        this.q2= checklist.isQ2();
        this.q3= checklist.getQ3();
        this.localDate=checklist.getCreatedAt().toLocalDate();
    }
}
