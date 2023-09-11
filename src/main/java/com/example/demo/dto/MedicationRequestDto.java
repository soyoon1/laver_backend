package com.example.demo.dto;

import lombok.*;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MedicationRequestDto {
    // 건강 관리 페이지에 쓰이는 Dto

    private int medicationId;
    private String medicationName;
    private LocalTime timeOfDay; // 복용해야 하는 시간
    private boolean didTake;
    private String img;
    private LocalTime timeOfTaking; //      복용한 시간


}
