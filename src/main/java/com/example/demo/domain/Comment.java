package com.example.demo.domain;

import com.example.demo.dto.BoardRequestDto;
import com.example.demo.dto.CommentRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL) // 또는 CascadeType.PERSIST 설정 가능
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "boardId")
    private Board board;



    public Comment(User user, CommentRequestDto requestDto, Board board) {
        this.content = requestDto.getContent();
        this.board = board;
        this.user=user;
    }



    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}