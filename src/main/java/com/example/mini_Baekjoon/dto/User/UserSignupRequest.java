package com.example.mini_Baekjoon.dto.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupRequest {
    private String userId;
    private String username;
    private String password;
}