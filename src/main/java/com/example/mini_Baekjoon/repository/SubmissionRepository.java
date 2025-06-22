package com.example.mini_Baekjoon.repository;

import com.example.mini_Baekjoon.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
    List<Submission> findByUserUserId(String userId);
}