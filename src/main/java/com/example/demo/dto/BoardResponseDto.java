//package com.example.demo.dto;
//
//import com.example.demo.domain.Board;
//import com.example.demo.domain.User;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import java.time.LocalDateTime;
//
//@NoArgsConstructor
//@Getter
//public class BoardResponseDto {
//
//    private String title;
//
//    private String content;
//
//    private LocalDateTime createdAt;
//
//    private LocalDateTime modifiedAt;
//
//    private String username;
//
////    @ManyToOne(fetch = FetchType.LAZY)
////    @JsonIgnore
////    @JoinColumn(name = "user_id")
////    private User user;
//
//
//
//    // board의 정보를 받아 boardResponseDto 생성
//    public BoardResponseDto(User user, Board board) {
//        this.username = user.getName();
//        this.title = board.getTitle();
//        this.content = board.getContent();
//        this.createdAt = board.getModifiedAt();
//        this.modifiedAt = board.getCreatedAt();
////        this.user=board.getUser();
//    }
//
//    public BoardResponseDto(Board board) {
//        this.title = board.getTitle();
//        this.content = board.getContent();
//        this.createdAt = board.getModifiedAt();
//        this.modifiedAt = board.getCreatedAt();
//        this.username = board.getUser().getName();
//    }
//}

package com.example.demo.dto;

import com.example.demo.domain.Board;
import com.example.demo.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class BoardResponseDto {

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String name;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnore
//    @JoinColumn(name = "user_id")
//    private User user;
//
//

    // board의 정보를 받아 boardResponseDto 생성
    public BoardResponseDto(User user, Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdAt = board.getModifiedAt();
        this.modifiedAt = board.getCreatedAt();
        this.name=user.getName();
    }

    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdAt = board.getModifiedAt();
        this.modifiedAt = board.getCreatedAt();
    }
}