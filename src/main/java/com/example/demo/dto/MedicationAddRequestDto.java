package com.example.demo.dto;

import lombok.*;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MedicationAddRequestDto {
    // 마이페이지 - 약 추가, 약 수정 Get, Put, 회원 가입(약 여러 개 버전, Dto 안에 DtoList로 들어감.) 시에 쓰이는 Dto
    private String medicationName;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;
    private LocalTime timeOfDay;

}
