package com.example.demo.service;



import com.example.demo.domain.User;

import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService{


    private Long expiredMs = 1000 * 60 * 60 * 24L;   // 1000 * 60 * 60l 1시간  * 24 해서 하루, 24시간으로 바꿈.

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final MedicationService medicationService;


//    @Transactional
//    @Override
//    public User getLoginUserByLoginId(String loginId){
//        if(loginId == null) return null;
//        Optional<User> optionalUser = memberRepository.findByLoginId(loginId);
//        if(optionalUser.isEmpty()) return null;
//        return optionalUser.get();
//    }

    @Transactional
    @Override
    public User getUserByUserId(int userId){
        Optional<User> optionalUser = memberRepository.findById(userId);

        if(optionalUser.isEmpty()){
            return null;
        }
        return optionalUser.get();
    }


    @Transactional
    @Override
    public User findUserById(int userId){
        return memberRepository.findById(userId).get();
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        return memberRepository.save(user);
    }

    @Transactional
    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }

        return null;
    }

}
