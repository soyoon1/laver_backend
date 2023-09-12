package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class ChecklistRequestDto {
    private boolean q1;
    private boolean q2;

    private String q3;

    private LocalDate localDate;
}
