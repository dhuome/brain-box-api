package com.example.brainBox.dtos;

import com.example.brainBox.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class CommentResponse {
    private String author;
    private String comment;
    private Timestamp createdAt;

    public static CommentResponse mapToCommentResponse(Comment comment) {
        return new CommentResponse(comment.getUser().getUsername(), comment.getComment(), comment.getCreatedAt());
    }
}
