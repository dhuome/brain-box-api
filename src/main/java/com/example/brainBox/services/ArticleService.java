package com.example.brainBox.services;

import com.example.brainBox.common.enums.AuthoritiesEnum;
import com.example.brainBox.common.enums.InteractionEnum;
import com.example.brainBox.dtos.*;
import com.example.brainBox.entities.Article;
import com.example.brainBox.entities.Comment;
import com.example.brainBox.entities.Interaction;
import com.example.brainBox.entities.User;
import com.example.brainBox.exceptions.BadRequestException;
import com.example.brainBox.exceptions.ForbiddenException;
import com.example.brainBox.repositories.ArticleRepository;
import com.example.brainBox.repositories.CommentRepository;
import com.example.brainBox.repositories.InteractionRepository;
import com.example.brainBox.specifications.ArticleSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.brainBox.common.constants.constants.ErrorMessages.*;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final UserService userService;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final ImageStorageService imageStorageService;
    private final InteractionRepository interactionRepository;

    public PaginatedResponse<ArticleResponse> getArticles(String q, Pageable pageable, Authentication authentication) {
        User user = userService.getUserIfExist(authentication);

        // Define sorting by createdAt in descending order
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Article> articles = articleRepository.findAll(ArticleSpecifications.titleContains(q, isAdmin(authentication)), sortedPageable);
        List<ArticleResponse> articleResponses = articles.getContent().stream().map(article -> {
            return getArticleResponse(user, article);
        }).collect(Collectors.toList());

        return PaginatedResponse.mapToPaginatedResponse(articleResponses, articles);
    }

    public ArticleResponse getArticle(Authentication authentication, Long articleId) {
        Long userId = (authentication != null) ? (Long) authentication.getPrincipal() : null;
        User user = (userId != null) ? userService.getUserById(userId) : null;
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new BadRequestException(ARTICLE_NOT_EXIST(articleId)));
        return getArticleResponse(user, article);
    }

    private ArticleResponse getArticleResponse(User user, Article article) {
        Long numberOfLikes = interactionRepository.countAllByInteractionAndArticle(InteractionEnum.LIKE, article);
        Long numberOfDislikes = interactionRepository.countAllByInteractionAndArticle(InteractionEnum.DISLIKE, article);
        ImageResponse imageResponse = imageStorageService.getImage(article.getImageUrl());
        Interaction interaction = (user != null) ? interactionRepository.findByUserAndArticle(user, article) : null;

        return ArticleResponse.mapToArticleResponse(article, numberOfLikes, numberOfDislikes, imageResponse, (interaction != null) ? interaction.getInteraction() : null);
    }

    public void createArticle(CreateArticleRequest createArticleRequest, MultipartFile imageFile, Long userId) {
        User user = userService.getUserById(userId);
        String imgUrl = imageStorageService.uploadImage(imageFile);
        createArticleRequest.setImgUrl(imgUrl);

        articleRepository.save(Article.mapToArticle(createArticleRequest, user));
    }

    public void deleteArticle(Long articleId, Long userId) {
        User user = userService.getUserById(userId);
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new BadRequestException(ARTICLE_NOT_EXIST(articleId)));

        if (!Objects.equals(user.getId(), article.getAuthor().getId())) {
            throw new ForbiddenException(NOT_ALLOWED_TO_DELETE_ARTICLE);
        }

        imageStorageService.deleteImage(article.getImageUrl());
        articleRepository.deleteById(articleId);
    }

    public void createComment(CreateCommentRequest createCommentRequest, Long articleId, Long userId) {
        User user = userService.getUserById(userId);
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new BadRequestException(ARTICLE_NOT_EXIST(articleId)));

        if (Objects.equals(user.getId(), article.getAuthor().getId())) {
            throw new ForbiddenException(NOT_ALLOWED_TO_POST_COMMENT);
        }

        commentRepository.save(new Comment(createCommentRequest.getComment(), user, article));
    }

    public PaginatedResponse<CommentResponse> getComments(Long articleId, Pageable pageable) {
        articleRepository.findById(articleId).orElseThrow(() -> new BadRequestException(ARTICLE_NOT_EXIST(articleId)));
        // Define sorting by createdAt in descending order
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Comment> commentPage = commentRepository.findByArticleId(articleId, sortedPageable);
        List<CommentResponse> commentResponses = commentPage.getContent().stream().map(CommentResponse::mapToCommentResponse).collect(Collectors.toList());
        return PaginatedResponse.mapToPaginatedResponse(commentResponses, commentPage);
    }

    public void addInteraction(Long articleId, Long userId, InteractionEnum interaction) {
        User user = userService.getUserById(userId);
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new BadRequestException(ARTICLE_NOT_EXIST(articleId)));

        if (Objects.equals(user.getId(), article.getAuthor().getId())) {
            throw new ForbiddenException(NOT_ALLOWED_TO_ADD_INTERACTION);
        }

        Interaction existingInteraction = interactionRepository.findByUserAndArticle(user, article);
        if (existingInteraction != null) {
            existingInteraction.setInteraction(interaction);
            interactionRepository.save(existingInteraction);
        } else {
            interactionRepository.save(new Interaction(interaction, user, article));
        }
    }

    public void toggleArticleStatus(Long articleId, Long userId, boolean status) {
        userService.getUserById(userId);
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new BadRequestException(ARTICLE_NOT_EXIST(articleId)));

        article.setDisabled(status);
        articleRepository.save(article);
    }

    public ImageResponse getImage(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new BadRequestException(ARTICLE_NOT_EXIST(articleId)));
        return imageStorageService.getImage(article.getImageUrl());
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication != null && authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(AuthoritiesEnum.ADMIN.toString()));
    }
}
