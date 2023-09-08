package com.example.demo.domain;


import com.example.demo.dto.BoardRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity
public class Board extends Timestamped {
    // 글 고유 아이디
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL) // 또는 CascadeType.PERSIST 설정 가능
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    // 글 제목
    @Column(nullable = false)
    private String title;

    // 글 내용
    @Column(nullable = false)
    private String content;


    @JsonIgnore
    @OneToMany(mappedBy = "board")
    List<Comment> comments = new ArrayList<>();


    // requestDto 정보를 가져와서 entity 만들 때 사용
    public Board(User user, BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.user=user;
    }

    // 업데이트 메소드
    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }




}