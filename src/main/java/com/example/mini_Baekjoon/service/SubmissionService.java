package com.example.mini_Baekjoon.service;

import com.example.mini_Baekjoon.entity.Submission;
import com.example.mini_Baekjoon.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;

    public List<Submission> findByUserId(String userId) {
        return submissionRepository.findByUserUserIdOrderBySubmittedAtDesc(userId);
    }
}