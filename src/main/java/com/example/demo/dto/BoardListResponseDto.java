package com.example.demo.dto;

import com.example.demo.domain.Board;
import com.example.demo.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardListResponseDto {
    // 제목
    private String title;

    //작성자명
    private String name;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnore
//    @JoinColumn(name = "user_id")
//    private User user;
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    // Entity -> dto
    public BoardListResponseDto(Board board) {
        this.title = board.getTitle();
        this.createdAt = board.getModifiedAt();
        this.modifiedAt = board.getCreatedAt();
        this.name=board.getUser().getName();
    }

//    public BoardListResponseDto(Optional<Board> board) {
//        this.title = board.get().getTitle();
//        this.createdAt = board.get().getModifiedAt();
//        this.modifiedAt = board.get().getCreatedAt();
//    }
}