package com.example.demo.repository;

import com.example.demo.domain.ChatRoom;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    List<ChatRoom> findByUser(User user);
    //List<ChatRoom> findAllByOrderByModifiedAtDesc();
}

