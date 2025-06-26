package com.example.mini_Baekjoon.repository;

import com.example.mini_Baekjoon.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
    List<Submission> findByUserUserId(String userId);
    List<Submission> findByUserUserIdOrderBySubmittedAtDesc(String userId);
}
