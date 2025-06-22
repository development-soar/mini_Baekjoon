package com.example.mini_Baekjoon.repository;

import com.example.mini_Baekjoon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUserId(String userId);
}