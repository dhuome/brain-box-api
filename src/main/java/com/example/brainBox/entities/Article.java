package com.example.brainBox.entities;

import com.example.brainBox.dtos.CreateArticleRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static com.example.brainBox.common.constants.constants.Defaults.MAX_BODY_LENGTH;
import static com.example.brainBox.common.constants.constants.Defaults.MAX_TITLE_LENGTH;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Article extends AuditEntity {
    @Column(nullable = false, length = MAX_TITLE_LENGTH)
    private String title;

    @Column(nullable = false, length = MAX_BODY_LENGTH)
    private String body;

    @Column(nullable = false)
    private boolean isDisabled = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Interaction> interactions;

    @Column(nullable = false)
    private String imageUrl;

    public static Article mapToArticle(CreateArticleRequest createArticleRequest, User user) {
        Article newArticle = new Article();

        newArticle.setAuthor(user);
        newArticle.setTitle(createArticleRequest.getTitle());
        newArticle.setBody(createArticleRequest.getBody());
        newArticle.setImageUrl(createArticleRequest.getImgUrl());

        return newArticle;
    }
}
