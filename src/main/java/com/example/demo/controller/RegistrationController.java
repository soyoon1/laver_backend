//package com.example.demo.controller;
//
//import com.example.demo.domain.User;
//import com.example.demo.dto.LoginRequestDto;
//import com.example.demo.dto.UserSignUpRequestDto;
//import com.example.demo.jwt.JwtUtil;
//import com.example.demo.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import javax.validation.Valid;
//
//@RequestMapping("/user")
//@RestController
//@RequiredArgsConstructor
//public class RegistrationController {
//
//
//    private final UserService userService;
//
//    UserSignUpRequestDto userSignUpRequestDtoStack = new UserSignUpRequestDto();
//
//    @Value("${spring.security.secret}")
//    private String secretKey;
//
//
//    @PostMapping("/personal-signup")
//    public String processPersonalSignup(@RequestBody @Valid UserSignUpRequestDto userSignUpRequestDto, BindingResult result) {
//
//        if(result.hasErrors()){
//            return "personal-signup";
//        }
//
//        userSignUpRequestDtoStack.setName(userSignUpRequestDto.getName());
//        userSignUpRequestDtoStack.setBirth(userSignUpRequestDto.getBirth());
//        userSignUpRequestDtoStack.setFcmToken(userSignUpRequestDto.getFcmToken());
//
//
//        return "redirect:/basic-signup";
//    }
//
//
//    @PostMapping("/basic-signup")
//    public String processBasicSignup(@RequestBody @Valid UserSignUpRequestDto userSignUpRequestDto, BindingResult result) {
//        if(result.hasErrors()){
//            return "basic-signup";
//        }
//
//        userSignUpRequestDtoStack.setLoginId(userSignUpRequestDto.getLoginId());
//        userSignUpRequestDtoStack.setPassword(userSignUpRequestDto.getPassword());
//        userSignUpRequestDtoStack.setCheckedPassword(userSignUpRequestDto.getCheckedPassword());
//
//        return "redirect:/medication-signup";
//    }
//
//
//    @PostMapping("/medication-signup")
//    public String processMedicationSignup(@RequestBody UserSignUpRequestDto userSignUpRequestDto) {
//
//        userSignUpRequestDtoStack.setMedicationName(userSignUpRequestDto.getMedicationName());
//        userSignUpRequestDtoStack.setMonday(userSignUpRequestDto.isMonday());
//        userSignUpRequestDtoStack.setTuesday(userSignUpRequestDto.isTuesday());
//        userSignUpRequestDtoStack.setWednesday(userSignUpRequestDto.isWednesday());
//        userSignUpRequestDtoStack.setThursday(userSignUpRequestDto.isThursday());
//        userSignUpRequestDtoStack.setFriday(userSignUpRequestDto.isFriday());
//        userSignUpRequestDtoStack.setSaturday(userSignUpRequestDto.isSaturday());
//        userSignUpRequestDtoStack.setSunday(userSignUpRequestDto.isSunday());
//        userSignUpRequestDtoStack.setTimeOfDay(userSignUpRequestDto.getTimeOfDay());
//
//
//        return "redirect:/profile-signup";
//    }
//
//
//    @PostMapping("/profile-signup")
//    public String processProfileSignup(@RequestBody UserSignUpRequestDto userSignUpRequestDto) throws Exception {
//        userSignUpRequestDtoStack.setNickname(userSignUpRequestDto.getNickname());
//        userSignUpRequestDtoStack.setSentence(userSignUpRequestDto.getSentence());
//
//        System.out.println(userSignUpRequestDtoStack);
//
//        userService.signUp(userSignUpRequestDtoStack);   // 최종적으로 DB에 저장
//
//
//        return "redirect:/signup-completed";   // 회원가입 완료 api 만들어야 함.
//    }
//
//
//    // 로그인
//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequestDto loginRequestDto){
//        User user = userService.login(loginRequestDto);
//
//        // 로그인 아이디나 비밀번호가 틀린 경우 global error return
//        if(user==null){
//            return "로그인 아이디 또는 비밀번호가 틀렸습니다.";
//        }
//
//        // 로그인 성공 => Jwt Token 발급
//
//        long expiredTimeMs = 1000 * 60 * 60* 24L;  // 1일, 24시간
//        String jwtToken = JwtUtil.createToken(user.getLoginId(), secretKey, expiredTimeMs);
//
//        return jwtToken;
//    }
//
//
//
//}


package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.UserSignUpRequestDto;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Slf4j
@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;
    UserSignUpRequestDto userSignUpRequestDtoStack = new UserSignUpRequestDto();


    @PostMapping("/signup")
    public String processSignup(@RequestBody @Valid UserSignUpRequestDto userSignUpRequestDto, BindingResult result) throws Exception {

        System.out.println(userSignUpRequestDto);

        if(result.hasErrors()) return "redirect:/signup";

        userService.signUp(userSignUpRequestDto); // 최종적으로 DB에 저장

        return "signup-completed";
    }



    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto){
        User user = userService.login(loginRequestDto);

        // 로그인 아이디나 비밀번호가 틀린 경우 global error return
        if(user==null){
            return "로그인 아이디 또는 비밀번호가 틀렸습니다.";
        }

        // 로그인 성공 => Jwt Token 발급

        long expiredTimeMs = 1000 * 60 * 60* 24L;  // 1일, 24시간
        String jwtToken;
        jwtToken = JwtUtil.createToken(user.getLoginId(), user.getRole(), expiredTimeMs);

        return jwtToken;
    }



}
