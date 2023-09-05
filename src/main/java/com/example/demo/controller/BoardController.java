package com.example.demo.controller;


import com.example.demo.domain.User;
import com.example.demo.dto.BoardListResponseDto;
import com.example.demo.dto.BoardRequestDto;
import com.example.demo.dto.BoardResponseDto;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.service.BoardService;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // JSON으로 데이터를 주고받음을 선언합니다.
public class BoardController {
    private final BoardService boardService;
    private final MemberRepository memberRepository;

    public BoardController(BoardService boardService, MemberRepository memberRepository) {
        this.boardService = boardService;
        this.memberRepository = memberRepository;
    }

//    // 글 등록
//    @PostMapping("/boards")
//    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto){
//        BoardResponseDto boardResponseDto = boardService.createBoard(user, requestDto);
//        return boardResponseDto;
//    }


    // 글 등록
    @PostMapping("/boards")
    public BoardResponseDto createBoard(User user, @RequestBody BoardRequestDto requestDto) {
        user = memberRepository.findById(JwtUtil.getCurrentMemberId()).orElseThrow(()-> new RuntimeException("로그인 유저 정보가 없습니다."));
        return boardService.createBoard(user, requestDto);
    }

    // 전체 목록 조회
    @GetMapping("/boards")
    public List<BoardListResponseDto> getAllBoards() {
        return boardService.findAllBoard();
    }

    // 글 하나 조회
    @GetMapping("/boards/{id}")
    public BoardResponseDto getOneBoard(@PathVariable Long id) {
        return boardService.findOneBoard(id);
    }

//    // 글 수정
//    @PutMapping("/boards/{id}")
//    public Long updateBoard(User user, @PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
//        return boardService.updateBoard(user, id,requestDto);
//    }
//
//    // 글 삭제
//    @DeleteMapping("/boards/{id}")
//    public Long deleteBoard(User user, @PathVariable Long id) {
//        return  boardService.deleteBoard(user, id);
//    }

}