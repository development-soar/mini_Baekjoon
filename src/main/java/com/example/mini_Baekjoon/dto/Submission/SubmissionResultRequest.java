package com.example.mini_Baekjoon.dto.Submission;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmissionResultRequest {
    private String result;
    private Double executionTime;
    private String language;
}