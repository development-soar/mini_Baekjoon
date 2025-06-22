package com.example.mini_Baekjoon.dto.User;

import com.example.mini_Baekjoon.dto.Submission.SubmissionDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMypageResponse {
    private UserInfoDto userInfo;
    private List<SubmissionDto> submissions;
}