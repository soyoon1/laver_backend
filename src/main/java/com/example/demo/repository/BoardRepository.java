package com.example.demo.repository;


import com.example.demo.domain.Board;
import com.example.demo.dto.BoardListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<BoardListResponseDto> findAllByOrderByModifiedAtDesc();
}