package com.example.demo.repository;

import com.example.demo.domain.Comment;
import com.example.demo.dto.CommentResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<CommentResponseDto> findAllByBoardId(Long boardId);
}