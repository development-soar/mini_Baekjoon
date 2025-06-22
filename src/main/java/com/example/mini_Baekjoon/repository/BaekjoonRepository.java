package com.example.mini_Baekjoon.repository;

import com.example.mini_Baekjoon.entity.Baekjoon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaekjoonRepository extends JpaRepository<Baekjoon, Integer> {
}