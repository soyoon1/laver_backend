package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDTO {
    // 알림 시 쓰이는 Dto
    private String targetToken;
    private String title;
    private String body;
    private String sound;

}
