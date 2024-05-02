package com.example.brainBox.repositories;

import com.example.brainBox.common.enums.InteractionEnum;
import com.example.brainBox.entities.Article;
import com.example.brainBox.entities.Interaction;
import com.example.brainBox.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {
    Interaction findByUserAndArticle(User user, Article article);

    Long countAllByInteractionAndArticle(InteractionEnum interaction, Article article);
}
