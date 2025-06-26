package com.example.mini_Baekjoon.service;

import com.example.mini_Baekjoon.dto.Submission.SubmissionRequest;
import com.example.mini_Baekjoon.entity.Baekjoon;
import com.example.mini_Baekjoon.entity.Submission;
import com.example.mini_Baekjoon.entity.User;
import com.example.mini_Baekjoon.repository.BaekjoonRepository;
import com.example.mini_Baekjoon.repository.SubmissionRepository;
import com.example.mini_Baekjoon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final BaekjoonRepository baekjoonRepository;
    private final UserRepository userRepository;

    public Submission saveSubmission(String userId,SubmissionRequest dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        Baekjoon problem = baekjoonRepository.findById(dto.getBaekjoonId())
                .orElseThrow(() -> new IllegalArgumentException("해당 문제를 찾을 수 없습니다."));

        Submission submission = Submission.builder()
                .user(user)
                .baekjoon(problem)
                .language(dto.getLanguage())
                .sourceCode(dto.getSourceCode())
                .result("PENDING")
                .submittedAt(LocalDateTime.now())
                .build();

        return submissionRepository.save(submission);
    }

    public List<Submission> findByUserId(String userId) {
        return submissionRepository.findByUserUserIdOrderBySubmittedAtDesc(userId);
    }
}