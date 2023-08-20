package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter @Setter
public class MedicationInsertDTO {
    // 테스트 코드 작성 시 쓰였던 Dto
    private String userId;
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
