package com.example.demo.dto;
import com.example.demo.domain.Board;
import com.example.demo.domain.Comment;
import com.example.demo.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentResponseDto {

    private Long boardId;

    private String content;
    private String name;

    public CommentResponseDto(User user,Comment comment, Board board) {
        this.content = comment.getContent();
        this.boardId = board.getId();
        this.name=user.getName();
    }

    public CommentResponseDto(Comment comment) {
        this.content = comment.getContent();
        this.boardId = comment.getBoard().getId();
        this.name=comment.getUser().getName();
    }

}
