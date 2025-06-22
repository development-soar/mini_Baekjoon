package com.example.mini_Baekjoon.dto.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    private String userId;
    private String username;
    private String createdAt;
}