package com.example.brainBox.entities;

import com.example.brainBox.common.enums.InteractionEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Interaction extends AuditEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InteractionEnum interaction;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id")
    private Article article;
}
