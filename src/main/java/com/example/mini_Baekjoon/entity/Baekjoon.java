package com.example.mini_Baekjoon.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Baekjoon {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer baekjoonId;

    private String title;
    private String description;

    @Column(columnDefinition = "TEXT")
    private String answerCode;

    @Column(columnDefinition = "TEXT")
    private String inputDescription;

    @Column(columnDefinition = "TEXT")
    private String outputDescription;

    private String algorithmCategory;

    @Column(updatable = false)
    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();
}
