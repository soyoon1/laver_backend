package com.example.demo.dto;
import com.example.demo.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class CommentRequestDto {

    private String content;

    private Long boardId;
    private String name;

    public CommentRequestDto(Comment comment) {
        this.content = comment.getContent();
        this.boardId = comment.getBoard().getId();
        this.name=comment.getUser().getName();
    }
}
