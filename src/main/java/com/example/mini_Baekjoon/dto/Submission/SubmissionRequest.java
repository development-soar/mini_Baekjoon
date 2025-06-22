package com.example.mini_Baekjoon.dto.Submission;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmissionRequest {
    private Integer baekjoonId;
    private String language;
    private String sourceCode;
}