package com.example.demo.dto;

import lombok.*;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MedicationListDto {
    // 메인 페이지에 약 리스트 줄 때 사용하는 Dto
    private int medicationId;
    private String medicationName;
    private LocalTime timeOfDay;
    private boolean didTake;
}
