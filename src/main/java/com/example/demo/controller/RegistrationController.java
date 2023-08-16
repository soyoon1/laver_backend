//package com.example.demo.controller;
//
//import com.example.demo.domain.User;
//import com.example.demo.dto.LoginRequestDto;
//import com.example.demo.dto.UserSignUpRequestDto;
//import com.example.demo.jwt.JwtUtil;
//import com.example.demo.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
//
//@RequestMapping("/user")
//@RestController
//public class RegistrationController {
//
//    @Autowired
//    private UserService userService;
//
//    @Value("${spring.security.secret}")
//    private String secretKey;
//
//    @GetMapping("/personal-signup")
//    public String showPersonalSignupForm(Model model) {
//        model.addAttribute("userSignUpRequestDto", new UserSignUpRequestDto());
//        return "personal-signup";
//    }
//
//    @PostMapping("/personal-signup")
//    public String processPersonalSignup(@ModelAttribute("userSignUpRequestDto") @Valid UserSignUpRequestDto userSignUpRequestDto, BindingResult result, HttpSession session) {
//
//        if(result.hasErrors()){
//            return "personal-signup";
//        }
//
//        session.setAttribute("userSignUpRequestDto", userSignUpRequestDto); // 세션에 user 정보 저장
//        return "redirect:/basic-signup";
//    }
//
//    @GetMapping("/basic-signup")
//    public String showBasicSignupForm(Model model) {
//        model.addAttribute("userSignUpRequestDto", new UserSignUpRequestDto());
//        return "basic-signup";
//    }
//
//    @PostMapping("/basic-signup")
//    public String processBasicSignup(@ModelAttribute("userSignUpRequestDto") @Valid UserSignUpRequestDto userSignUpRequestDto, BindingResult result, HttpSession session) {
//       if(result.hasErrors()){
//           return "basic-signup";
//       }
//        // 세션에서 이전 단계에서 저장한 user 정보 가져오기
//        UserSignUpRequestDto step1Dto = (UserSignUpRequestDto) session.getAttribute("userSignUpRequestDto");
//        // 이전 단계에서 저장한 정보와 병합하여 user 정보 업데이트
//        step1Dto.setLoginId(userSignUpRequestDto.getLoginId());
//        step1Dto.setPassword(userSignUpRequestDto.getPassword());
//        step1Dto.setCheckedPassword(userSignUpRequestDto.getCheckedPassword());
//
//        session.setAttribute("userSignUpRequestDto", step1Dto); // 세션에 user 정보 저장
//        return "redirect:/medication-signup";
//    }
//
//    @GetMapping("/medication-signup")
//    public String showMedicationSignupForm(Model model) {
//        // 약 리스트 관련 정보 입력 폼
//        model.addAttribute("userSignUpRequestDto", new UserSignUpRequestDto());
//        return "medication-signup";
//    }
//
//    @PostMapping("/medication-signup")
//    public String processMedicationSignup(@ModelAttribute("userSignUpRequestDto") UserSignUpRequestDto userSignUpRequestDto, HttpSession session) {
//        // 세션에서 이전 단계에서 저장한 user 정보 가져오기
//        UserSignUpRequestDto step2Dto = (UserSignUpRequestDto) session.getAttribute("userSignUpRequestDto");
//
//        // step2Dto에 약 리스트 관련 정보 추가
//        step2Dto.setMedicationName(userSignUpRequestDto.getMedicationName());
//        step2Dto.setMonday(userSignUpRequestDto.isMonday());
//        step2Dto.setTuesday(userSignUpRequestDto.isTuesday());
//        step2Dto.setWednesday(userSignUpRequestDto.isWednesday());
//        step2Dto.setThursday(userSignUpRequestDto.isThursday());
//        step2Dto.setFriday(userSignUpRequestDto.isFriday());
//        step2Dto.setSaturday(userSignUpRequestDto.isSaturday());
//        step2Dto.setSunday(userSignUpRequestDto.isSunday());
//        step2Dto.setTimeOfDay(userSignUpRequestDto.getTimeOfDay());
//
//        session.setAttribute("userSignUpRequestDto", step2Dto); // 세션에 user 정보 저장
//        return "redirect:/profile-signup";
//    }
//
//    @GetMapping("/profile-signup")
//    public String showProfileSignupForm(Model model) {
//        model.addAttribute("userSignUpRequestDto", new UserSignUpRequestDto());
//        return "profile-signup";
//    }
//
//    @PostMapping("/profile-signup")
//    public String processProfileSignup(@ModelAttribute("userSignUpRequestDto") UserSignUpRequestDto userSignUpRequestDto, HttpSession session) throws Exception {
//        // 세션에서 이전 단계에서 저장한 user 정보 가져오기
//        UserSignUpRequestDto step3Dto = (UserSignUpRequestDto) session.getAttribute("userSignUpRequestDto");
//        // 이전 단계에서 저장한 정보와 병합하여 user 정보 업데이트
//        step3Dto.setNickname(userSignUpRequestDto.getNickname());
//        step3Dto.setSentence(userSignUpRequestDto.getSentence());
//
//        System.out.println(step3Dto);
//
//        userService.signUp(step3Dto);   // 최종적으로 DB에 저장
//
//        session.removeAttribute("userSignUpRequestDto");
//
//        return "redirect:/signup-completed";   // 회원가입 완료 api 만들어야 함.
//    }
//
//    @GetMapping("/signup-completed")
//    public String showRegistrationCompleted() {
//        return "signup-completed";
//    }
//
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
//        long expiredTimeMs = 1000 * 60 * 60L;
//        String jwtToken = JwtUtil.createToken(user.getLoginId(), secretKey, expiredTimeMs);
//
//        return jwtToken;
//
//    }
//
//
//
//}

//package com.example.demo.controller;
//
//import com.example.demo.domain.User;
//import com.example.demo.dto.LoginRequestDto;
//import com.example.demo.dto.UserSignUpRequestDto;
//import com.example.demo.jwt.JwtUtil;
//import com.example.demo.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
//
//@RequestMapping("/user")
//@RestController
//public class RegistrationController {
//
//    @Autowired
//    private UserService userService;
//
//    UserSignUpRequestDto userSignUpRequestDtoStack = new UserSignUpRequestDto();
//
//    @Value("${spring.security.secret}")
//    private String secretKey;
//
//    @GetMapping("/personal-signup")
//    public String showPersonalSignupForm(Model model) {
//        model.addAttribute("userSignUpRequestDto", new UserSignUpRequestDto());
//        return "personal-signup";
//    }
//
//    @PostMapping("/personal-signup")
//    public String processPersonalSignup(@ModelAttribute("userSignUpRequestDto") @Valid UserSignUpRequestDto userSignUpRequestDto, BindingResult result) {
//
//        if(result.hasErrors()){
//            return "personal-signup";
//        }
//
//        userSignUpRequestDtoStack.setName(userSignUpRequestDto.getName());
//        userSignUpRequestDtoStack.setBirth(userSignUpRequestDto.getBirth());
//
//        return "redirect:/basic-signup";
//    }
//
//    @GetMapping("/basic-signup")
//    public String showBasicSignupForm(Model model) {
//        model.addAttribute("userSignUpRequestDto", new UserSignUpRequestDto());
//        return "basic-signup";
//    }
//
//    @PostMapping("/basic-signup")
//    public String processBasicSignup(@ModelAttribute("userSignUpRequestDto") @Valid UserSignUpRequestDto userSignUpRequestDto, BindingResult result) {
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
//    @GetMapping("/medication-signup")
//    public String showMedicationSignupForm(Model model) {
//        // 약 리스트 관련 정보 입력 폼
//        model.addAttribute("userSignUpRequestDto", new UserSignUpRequestDto());
//        return "medication-signup";
//    }
//
//    @PostMapping("/medication-signup")
//    public String processMedicationSignup(@ModelAttribute("userSignUpRequestDto") UserSignUpRequestDto userSignUpRequestDto) {
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
//    @GetMapping("/profile-signup")
//    public String showProfileSignupForm(Model model) {
//        model.addAttribute("userSignUpRequestDto", new UserSignUpRequestDto());
//        return "profile-signup";
//    }
//
//    @PostMapping("/profile-signup")
//    public String processProfileSignup(@ModelAttribute("userSignUpRequestDto") UserSignUpRequestDto userSignUpRequestDto) throws Exception {
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
//    @GetMapping("/signup-completed")
//    public String showRegistrationCompleted() {
//        return "signup-completed";
//    }
//
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
//        long expiredTimeMs = 1000 * 60 * 60L;
//        String jwtToken = JwtUtil.createToken(user.getLoginId(), secretKey, expiredTimeMs);
//
//        return jwtToken;
//
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequestMapping("/user")
@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    UserSignUpRequestDto userSignUpRequestDtoStack = new UserSignUpRequestDto();

    @Value("${spring.security.secret}")
    private String secretKey;


    @PostMapping("/personal-signup")
    public String processPersonalSignup(@RequestBody @Valid UserSignUpRequestDto userSignUpRequestDto, BindingResult result) {

        if(result.hasErrors()){
            return "personal-signup";
        }

        userSignUpRequestDtoStack.setName(userSignUpRequestDto.getName());
        userSignUpRequestDtoStack.setBirth(userSignUpRequestDto.getBirth());
        userSignUpRequestDtoStack.setFcmToken(userSignUpRequestDto.getFcmToken());


        return "redirect:/basic-signup";
    }


    @PostMapping("/basic-signup")
    public String processBasicSignup(@RequestBody @Valid UserSignUpRequestDto userSignUpRequestDto, BindingResult result) {
        if(result.hasErrors()){
            return "basic-signup";
        }

        userSignUpRequestDtoStack.setLoginId(userSignUpRequestDto.getLoginId());
        userSignUpRequestDtoStack.setPassword(userSignUpRequestDto.getPassword());
        userSignUpRequestDtoStack.setCheckedPassword(userSignUpRequestDto.getCheckedPassword());

        return "redirect:/medication-signup";
    }


    @PostMapping("/medication-signup")
    public String processMedicationSignup(@RequestBody UserSignUpRequestDto userSignUpRequestDto) {

        userSignUpRequestDtoStack.setMedicationName(userSignUpRequestDto.getMedicationName());
        userSignUpRequestDtoStack.setMonday(userSignUpRequestDto.isMonday());
        userSignUpRequestDtoStack.setTuesday(userSignUpRequestDto.isTuesday());
        userSignUpRequestDtoStack.setWednesday(userSignUpRequestDto.isWednesday());
        userSignUpRequestDtoStack.setThursday(userSignUpRequestDto.isThursday());
        userSignUpRequestDtoStack.setFriday(userSignUpRequestDto.isFriday());
        userSignUpRequestDtoStack.setSaturday(userSignUpRequestDto.isSaturday());
        userSignUpRequestDtoStack.setSunday(userSignUpRequestDto.isSunday());
        userSignUpRequestDtoStack.setTimeOfDay(userSignUpRequestDto.getTimeOfDay());


        return "redirect:/profile-signup";
    }


    @PostMapping("/profile-signup")
    public String processProfileSignup(@RequestBody UserSignUpRequestDto userSignUpRequestDto) throws Exception {
        userSignUpRequestDtoStack.setNickname(userSignUpRequestDto.getNickname());
        userSignUpRequestDtoStack.setSentence(userSignUpRequestDto.getSentence());

        System.out.println(userSignUpRequestDtoStack);

        userService.signUp(userSignUpRequestDtoStack);   // 최종적으로 DB에 저장


        return "redirect:/signup-completed";   // 회원가입 완료 api 만들어야 함.
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
        String jwtToken = JwtUtil.createToken(user.getLoginId(), secretKey, expiredTimeMs);

        return jwtToken;
    }



}
