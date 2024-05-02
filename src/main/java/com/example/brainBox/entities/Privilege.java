package com.example.brainBox.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Privilege extends BaseEntity {
    @Column(unique = true, nullable = false, columnDefinition = "nvarchar(50)")
    private String nameAr, nameEn;

    @ManyToMany(mappedBy = "privileges")
    private List<User> users;
}
