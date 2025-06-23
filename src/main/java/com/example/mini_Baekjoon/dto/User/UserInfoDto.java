package com.example.mini_Baekjoon.dto.User;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter @Setter
public class UserInfoDto {
    private String userId;
    private String username;
    private LocalDateTime createdAt;

    public UserInfoDto(String userId, String username, LocalDateTime createdAt) {
        this.userId = userId;
        this.username = username;
        this.createdAt = createdAt;
    }
}