package com.example.mini_Baekjoon.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "User")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(length = 50)
    private String userId;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();
}
