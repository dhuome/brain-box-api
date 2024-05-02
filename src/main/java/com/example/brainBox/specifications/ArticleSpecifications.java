package com.example.brainBox.specifications;

import com.example.brainBox.entities.Article;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ArticleSpecifications {
    public static Specification<Article> titleContains(String title, Boolean isAdmin) {
        return (root, query, criteriaBuilder) -> {
            if (isAdmin != null && isAdmin) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
            } else {
                return criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"), criteriaBuilder.equal(root.get("isDisabled"), false));
            }
        };
    }
}
