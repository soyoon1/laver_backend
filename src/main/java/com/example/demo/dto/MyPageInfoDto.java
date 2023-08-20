package com.example.demo.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MyPageInfoDto {
    // 마이페이지 Get 할 때 쓰이는 Dto
    private String username;
    private String sentence;
    private List<MedicationInfoDto> medications;
}
