package com.example.demo.controller;


import com.example.demo.domain.User;
import com.example.demo.dto.BoardListResponseDto;
import com.example.demo.dto.BoardRequestDto;
import com.example.demo.dto.BoardResponseDto;
import com.example.demo.service.BoardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // JSON으로 데이터를 주고받음을 선언합니다.
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 글 등록
    @PostMapping("/boards")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto){
        BoardResponseDto boardResponseDto = boardService.createBoard(requestDto);
        return boardResponseDto;
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

    // 글 수정
    @PutMapping("/boards/{id}")
    public Long updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.updateBoard(id,requestDto);
    }

    // 글 삭제
    @DeleteMapping("/boards/{id}")
    public Long deleteBoard(@PathVariable Long id) {
        return  boardService.deleteBoard(id);
    }

//    // 비밀번호 확인
//    @GetMapping("/boards/check/{id}/{inputPassword}")
//    public boolean checkPassword(@PathVariable Long id,@PathVariable String inputPassword) {
//        return boardService.checkPassword(id, inputPassword);
//    }
}