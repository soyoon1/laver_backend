package com.example.demo.dto;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import lombok.*;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserSignUpDto {

    // 회원 가입 시 쓰이는 Dto 약 여러 개를 저장할 수 있는 버전
    private String name; // 이름

    private Date birth; // 생년월일

    private String loginId; // 아이디

    private String password; // 비밀번호

    private String checkedPassword; // 비밀번호 확인

    private Role role;

    // 약 정보

    List<MedicationAddRequestDto> medicationAddRequestDtoList;

    private String nickname;

    private String sentence;

    private String fcmToken;

    private boolean alarm;

    private Date createdDate;

    @Builder
    public User toEntity(){
        return User.builder()
                .name(name)
                .birth(birth)
                .loginId(loginId)
                .password(password)
                .nickname(nickname)
                .sentence(sentence)
                .fcmToken(fcmToken)
                .role(Role.USER)
                .alarm(alarm)
                .createdDate(new Date())
                .build();
    }


}
