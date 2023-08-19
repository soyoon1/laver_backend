package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLoginId(String loginId);

    Optional<User> findById(int id);

//    @Query("SELECT u FROM User u JOIN FETCH u.medications WHERE u.id = :userId")
//    User findUserWithMedications(@Param("userId") int userId);
}
