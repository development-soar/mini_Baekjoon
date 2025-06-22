package com.example.mini_Baekjoon.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Submission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer submissionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "baekjoon_id")
    private Baekjoon baekjoon;

    private String language;
    private String sourceCode;
    private String result;
    private Double executionTime;
    private java.time.LocalDateTime submittedAt = java.time.LocalDateTime.now();
}
