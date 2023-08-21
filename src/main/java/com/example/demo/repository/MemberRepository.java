package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<User, Integer> {

    Optional <User> findById(int id);


    Optional<User> findByLoginId(String loginId);



}
