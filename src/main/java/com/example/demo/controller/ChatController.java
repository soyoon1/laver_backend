package com.example.demo.controller;

import com.example.demo.domain.ChatRoom;
import com.example.demo.domain.User;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.ChatRoomRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.ChatService;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("api/chat")
public class ChatController {
    private final ChatService chatService;
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;

//    public ChatController(ChatService chatService, MemberRepository memberRepository, ChatRoomRepository chatRoomRepository) {
//        this.chatService = chatService;
//        this.memberRepository = memberRepository;
//        this.chatRoomRepository=chat
//    }
//
    @PostMapping("/api/checklist")
    public ChatRoom createRoom(User user,
                               @RequestParam("partner") int partnerId) {
        user = memberRepository.findById(JwtUtil.getCurrentMemberId()).orElseThrow(()-> new RuntimeException("로그인 유저 정보가 없습니다."));
        User partner=memberRepository.findById(partnerId).get();
        return chatService.createRoom(user, partner);
    }

    @GetMapping("/api/checklist")
    public List<ChatRoom> getCurrentUserChatRooms() {
        return chatService.getCurrentUserChatRooms();
    }
}