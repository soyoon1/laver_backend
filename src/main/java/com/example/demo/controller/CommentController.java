package com.example.demo.controller;

import com.example.demo.domain.Comment;
import com.example.demo.domain.User;
import com.example.demo.dto.CommentRequestDto;
import com.example.demo.dto.CommentResponseDto;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.BoardService;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CommentController {
    private final CommentService commentService;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;


    public CommentController(CommentService commentService, MemberRepository memberRepository, BoardRepository boardRepository) {
        this.commentService = commentService;
        this.memberRepository = memberRepository;
        this.boardRepository=boardRepository;
    }

    // 댓글 등록
    @PostMapping("/api/boards/{id}/comments")
    public CommentResponseDto createBoard(User user, @RequestBody CommentRequestDto requestDto, @PathVariable Long id) {
        user = memberRepository.findById(JwtUtil.getCurrentMemberId()).orElseThrow(()-> new RuntimeException("로그인 유저 정보가 없습니다."));
        return commentService.createComment(user, requestDto, id);
    }

    // 댓글 목록 조회
    @GetMapping("/api/boards/{id}/comments")
    public List<CommentResponseDto> getAllComments(@PathVariable Long id) {
        return commentService.readAllComments(id);
    }
}