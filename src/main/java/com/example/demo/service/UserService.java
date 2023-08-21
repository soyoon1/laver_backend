package com.example.demo.service;

import com.example.demo.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public interface UserService {


    public User getUserByUserId(int userId);

    public User findUserById(int userId);
    public User saveUser(User user);

    public User getCurrentUser();

}


//package com.example.demo.service;
//import com.example.demo.domain.User;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
////
//    public User getCurrentUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.getPrincipal() instanceof User) {
//            return (User) authentication.getPrincipal();
//        }
//
//        return null;
//    }
//}