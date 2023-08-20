package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.MyPageInfoDto;
import com.example.demo.dto.UserSignUpDto;
import com.example.demo.dto.UserSignUpRequestDto;

public interface UserService {

    // 회원가입
    public Integer signUp(UserSignUpRequestDto requestDto) throws Exception;  // 이제 안 쓰임.
    public Integer signUp(UserSignUpDto requestDto) throws Exception;

    public User getLoginUserByLoginId(String loginId);

    public User login(LoginRequestDto loginRequestDto);

    public User getUserByUserId(int userId);
    public MyPageInfoDto getMyPageInfoByUserId(int userId);
    public User findUserById(int userId);
    public User saveUser(User user);
    public void updateAlarmSetting(int userId, boolean alarm);
}
