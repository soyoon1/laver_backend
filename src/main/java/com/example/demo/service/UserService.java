package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.UserSignUpRequestDto;

public interface UserService {

    // 회원가입
    public Integer signUp(UserSignUpRequestDto requestDto) throws Exception;

    public User getLoginUserByLoginId(String loginId);

    public User login(LoginRequestDto loginRequestDto);
}
