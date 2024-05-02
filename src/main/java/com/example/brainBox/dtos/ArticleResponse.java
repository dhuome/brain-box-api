package com.example.brainBox.dtos;

import com.example.brainBox.common.enums.InteractionEnum;
import com.example.brainBox.entities.Article;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class ArticleResponse {
    private Long id;
    private Long authorId;
    private String author;
    private String title;
    private String body;
    private Timestamp createdAt;
    private Long numberOfLikes;
    private Long numberOfDislikes;
    private Boolean isDisabled;
    private ImageResponse image;
    private InteractionEnum interaction;

    public static ArticleResponse mapToArticleResponse(Article article, Long numberOfLikes, Long numberOfDislikes, ImageResponse image, InteractionEnum interaction) {
        return new ArticleResponse(article.getId(), article.getAuthor().getId(), article.getAuthor().getUsername(), article.getTitle(), article.getBody(), article.getCreatedAt(), numberOfLikes, numberOfDislikes, article.isDisabled(), image, interaction);
    }
}
