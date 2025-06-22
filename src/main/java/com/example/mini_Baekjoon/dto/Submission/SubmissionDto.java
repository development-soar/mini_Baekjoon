package com.example.mini_Baekjoon.dto.Submission;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubmissionDto {
    private Integer submissionId;
    private Integer baekjoonId;
    private String language;
    private String result;
    private Double executionTime;
    private String submittedAt;
}