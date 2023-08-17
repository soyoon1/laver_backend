package com.example.demo.dto;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@Getter @Setter
@NoArgsConstructor
public class UserSignUpRequestDto {

  //  @NotBlank(message = "이름을 입력해주세요")
    private String name; // 이름

   // @NotBlank(message = "생년월일을 선택해주세요")
   // @NotNull(message = "생년월일을 선택해주세요")
    private Date birth; // 생년월일

  //  @NotBlank(message = "아이디를 입력해주세요")
    private String loginId; // 아이디

  //  @NotBlank(message = "비밀번호를 입력해주세요")
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\\\d)(?=.*[@$!%*#?&])[A-Za-z\\\\d@$!%*#?&]{8,30}$",
//             message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야 합니다.")
    private String password; // 비밀번호

 //   @NotBlank(message = "비밀번호가 다릅니다")
    private String checkedPassword; // 비밀번호 확인

    private Role role;

    // 약 정보
    private String medicationName; // 약 이름
    private boolean monday;  // 요일 선택
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;
    private LocalTime timeOfDay;  // 복용 시간

 //   @NotBlank(message = "닉네임을 입력해주세요")
    private String nickname;

    private String sentence;

    private String fcmToken;

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
                .build();
    }

}
