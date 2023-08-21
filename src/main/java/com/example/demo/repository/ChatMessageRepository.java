package com.example.demo.repository;

import com.example.demo.domain.ChatMessage;
import com.example.demo.domain.ChatRoom;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface
ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    //List<ChatMessage> findByUser(User user);
}
