package com.example.demo.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MedicationInfoDto {
    // 마이페이지 Get 할 때 쓰이는 Dto
    private int medicationId;
    private String medicationName;

}
