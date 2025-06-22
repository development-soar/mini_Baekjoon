package com.example.mini_Baekjoon.dto.Baekjoon;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaekjoonDetailDto {
    private Integer baekjoonId;
    private String title;
    private String description;
    private String inputDescription;
    private String outputDescription;
    private String answerCode;
    private String algorithmCategory;
}