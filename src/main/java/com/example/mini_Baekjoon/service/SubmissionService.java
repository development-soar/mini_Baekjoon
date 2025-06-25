package com.example.mini_Baekjoon.service;

import com.example.mini_Baekjoon.dto.Submission.SubmissionRequest;
import com.example.mini_Baekjoon.entity.Baekjoon;
import com.example.mini_Baekjoon.entity.Submission;
import com.example.mini_Baekjoon.repository.BaekjoonRepository;
import com.example.mini_Baekjoon.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final BaekjoonRepository baekjoonRepository;

    public Submission saveSubmission(SubmissionRequest dto) {
        Baekjoon problem = baekjoonRepository.findById(dto.getBaekjoonId())
                .orElseThrow(() -> new IllegalArgumentException("해당 문제를 찾을 수 없습니다."));

        Submission submission = Submission.builder()
                .baekjoon(problem)
                .language(dto.getLanguage())
                .sourceCode(dto.getSourceCode())
                .result("PENDING") // 채점 전 상태
                .submittedAt(LocalDateTime.now())
                .build();

        return submissionRepository.save(submission);
    }

    public List<Submission> findByUserId(String userId) {
        return submissionRepository.findByUserUserIdOrderBySubmittedAtDesc(userId);
    }
}