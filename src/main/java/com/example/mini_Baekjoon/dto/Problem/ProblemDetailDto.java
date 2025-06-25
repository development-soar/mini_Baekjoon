package com.example.mini_Baekjoon.dto.Problem;

public class ProblemDetailDto {
    private int baekjoonId;
    private String title;
    private String description;
    private String inputDescription;
    private String outputDescription;
    private String answerCode;
    private String algorithmCategory;

    public ProblemDetailDto(int baekjoonId, String title, String description,
                            String inputDescription, String outputDescription,
                            String answerCode, String algorithmCategory) {
        this.baekjoonId = baekjoonId;
        this.title = title;
        this.description = description;
        this.inputDescription = inputDescription;
        this.outputDescription = outputDescription;
        this.answerCode = answerCode;
        this.algorithmCategory = algorithmCategory;
    }

    public int getBaekjoonId() { return baekjoonId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getInputDescription() { return inputDescription; }
    public String getOutputDescription() { return outputDescription; }
    public String getAnswerCode() { return answerCode; }
    public String getAlgorithmCategory() { return algorithmCategory; }
}

