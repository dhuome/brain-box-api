package com.example.brainBox.entities;

import com.example.brainBox.dtos.CreateUserRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "platform_users")
public class User extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String username, mobileNumber, email;

    @Column(nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(name = "user_privilege", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    private List<Privilege> privileges;

    @OneToMany(mappedBy = "author")
    private List<Article> articles;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    public static User maptoUser(CreateUserRequest createUserRequest, Privilege privilege, String encodedPassword) {
        User newUser = new User();
        newUser.setUsername(createUserRequest.getUsername());
        newUser.setEmail(createUserRequest.getEmail());
        newUser.setMobileNumber(createUserRequest.getMobileNumber());
        newUser.setPassword(encodedPassword);
        newUser.setPrivileges(List.of(privilege));

        return newUser;
    }
}
