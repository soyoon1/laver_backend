package com.example.demo.repository;

import com.example.demo.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface
ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    //List<ChatMessage> findByUser(User user);
}