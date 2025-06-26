package com.example.mini_Baekjoon.dto.Problem;

public class ProblemSummaryDto {
    private int baekjoonId;
    private String title;
    private String algorithmCategory;

    public ProblemSummaryDto(int baekjoonId, String title, String algorithmCategory) {
        this.baekjoonId = baekjoonId;
        this.title = title;
        this.algorithmCategory = algorithmCategory;
    }

    public int getBaekjoonId() { return baekjoonId; }
    public String getTitle() { return title; }
    public String getAlgorithmCategory() { return algorithmCategory; }
}