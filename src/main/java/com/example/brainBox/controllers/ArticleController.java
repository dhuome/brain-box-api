package com.example.brainBox.controllers;

import com.example.brainBox.common.constraints.FileSize;
import com.example.brainBox.common.enums.InteractionEnum;
import com.example.brainBox.dtos.*;
import com.example.brainBox.exceptions.BadRequestException;
import com.example.brainBox.services.ArticleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

import static com.example.brainBox.common.constants.constants.Defaults.MAX_FILE_SIZE;
import static com.example.brainBox.common.constants.constants.Endpoints.ARTICLE;
import static com.example.brainBox.common.constants.constants.Endpoints.BASE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL + ARTICLE)
public class ArticleController {
    private final ArticleService articleService;
    private final Validator validator;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> createArticle(@Valid @RequestPart(value = "data") String createArticleRequest, @RequestPart(value = "image") @FileSize(max = MAX_FILE_SIZE) MultipartFile image, Authentication authentication) throws JsonProcessingException {
        Long userId = (Long) authentication.getPrincipal();

        CreateArticleRequest req = new ObjectMapper().readValue(createArticleRequest, CreateArticleRequest.class);
        Set<ConstraintViolation<Object>> violations = validator.validate(req);

        if (!violations.isEmpty()) {
            throw new BadRequestException(violations);
        }

        articleService.createArticle(req, image, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<ArticleResponse>> getArticles(@RequestParam(value = "query", required = false, defaultValue = "") String query, @PageableDefault Pageable pageable, Authentication authentication) {
        return ResponseEntity.ok(articleService.getArticles(query, pageable, authentication));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable("id") Long id, Authentication authentication) {
        return new ResponseEntity<>(articleService.getArticle(authentication, id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        articleService.deleteArticle(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{id}/comment")
    public ResponseEntity<Void> postComment(@Valid @RequestBody CreateCommentRequest createCommentRequest, @PathVariable("id") Long id, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        articleService.createComment(createCommentRequest, id, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<PaginatedResponse<CommentResponse>> getComments(@PathVariable("id") Long articleId, @PageableDefault Pageable pageable) {
        return new ResponseEntity<>(articleService.getComments(articleId, pageable), HttpStatus.OK);
    }

    // Suggestion: Consider consolidating the updateInteraction endpoints into one, utilizing RequestParam for the interaction enum from the frontend.
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{id}/like")
    public ResponseEntity<Void> likeArticle(@PathVariable("id") Long articleId, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        articleService.addInteraction(articleId, userId, InteractionEnum.LIKE);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{id}/dislike")
    public ResponseEntity<Void> dislikeArticle(@PathVariable("id") Long articleId, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        articleService.addInteraction(articleId, userId, InteractionEnum.DISLIKE);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Suggestion: Same thing here regarding the interaction endpoints
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/enable")
    public ResponseEntity<Void> enableArticle(@PathVariable("id") Long articleId, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        articleService.toggleArticleStatus(articleId, userId, false);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/disable")
    public ResponseEntity<Void> disableArticle(@PathVariable("id") Long articleId, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        articleService.toggleArticleStatus(articleId, userId, true);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<ImageResponse> getImage(@PathVariable("id") Long articleId) {
        return new ResponseEntity<>(articleService.getImage(articleId), HttpStatus.OK);
    }
}
