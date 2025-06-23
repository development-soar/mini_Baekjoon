package com.example.mini_Baekjoon.dto.Submission;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class SubmissionDto {
    private Integer submissionId;
    private Integer baekjoonId;
    private String title;
    private String language;
    private String result;
    private Double executionTime;
    private LocalDateTime submittedAt;

    public SubmissionDto(Integer submissionId, Integer baekjoonId, String title,
                         String language, String result, Double executionTime,
                         LocalDateTime submittedAt) {
        this.submissionId = submissionId;
        this.baekjoonId = baekjoonId;
        this.title = title;
        this.language = language;
        this.result = result;
        this.executionTime = executionTime;
        this.submittedAt = submittedAt;
    }
}
