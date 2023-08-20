package com.example.demo.dto;

import lombok.*;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserSettingsDto {
    // 마이페이지 설정페이지 시 쓰이는 Dto
    private String name;
    private String sentence;
    private String nickname;
    private boolean alarm;
}
