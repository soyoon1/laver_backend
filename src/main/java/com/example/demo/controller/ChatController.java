package com.example.demo.controller;

import com.example.demo.domain.ChatRoom;
import com.example.demo.domain.User;
import com.example.demo.repository.ChatRoomRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;

    @PostMapping
    public ChatRoom createRoom(@RequestParam("user") int userId,
                               @RequestParam("partner") int partnerId) {
        User user=memberRepository.findById(userId).get();
        User partner=memberRepository.findById(partnerId).get();
        return chatService.createRoom(user, partner);
    }

    @GetMapping
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }
}