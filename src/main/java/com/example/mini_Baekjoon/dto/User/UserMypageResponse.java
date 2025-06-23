package com.example.mini_Baekjoon.dto.User;

import com.example.mini_Baekjoon.dto.Submission.SubmissionDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class UserMypageResponse {
    private UserInfoDto userInfo;
    private List<SubmissionDto> submissions;

    public UserMypageResponse(UserInfoDto userInfo, List<SubmissionDto> submissions) {
        this.userInfo = userInfo;
        this.submissions = submissions;
    }
}
