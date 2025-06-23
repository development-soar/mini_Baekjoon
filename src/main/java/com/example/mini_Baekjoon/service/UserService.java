package com.example.mini_Baekjoon.service;

import com.example.mini_Baekjoon.config.security.jwt.JwtTokenProvider;
import com.example.mini_Baekjoon.dto.Submission.SubmissionDto;
import com.example.mini_Baekjoon.dto.User.UserInfoDto;
import com.example.mini_Baekjoon.dto.User.UserLoginRequest;
import com.example.mini_Baekjoon.dto.User.UserMypageResponse;
import com.example.mini_Baekjoon.dto.User.UserSignupRequest;
import com.example.mini_Baekjoon.entity.Baekjoon;
import com.example.mini_Baekjoon.entity.User;
import com.example.mini_Baekjoon.repository.SubmissionRepository;
import com.example.mini_Baekjoon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SubmissionRepository submissionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Transactional
    public void signup(UserSignupRequest request) {
        if (userRepository.existsById(request.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 기본값 설정 추가
        User user = User.builder()
                .userId(request.getUserId())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }

    public String login(UserLoginRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        return jwtTokenProvider.createToken(user.getUserId());
    }

    @Transactional(readOnly = true)
    public UserMypageResponse getMyPage(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        List<SubmissionDto> submissions = Collections.emptyList();
        try {
            submissions = submissionRepository.findByUserUserId(userId)
                    .stream()
                    .map(submission -> {
                        Baekjoon baekjoon = submission.getBaekjoon();
                        return new SubmissionDto(
                                submission.getSubmissionId(),
                                baekjoon != null ? baekjoon.getBaekjoonId() : 0,
                                baekjoon != null ? baekjoon.getTitle() : "알 수 없음",
                                submission.getLanguage(),
                                submission.getResult(),
                                submission.getExecutionTime(),
                                submission.getSubmittedAt()
                        );
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("제출 기록 조회 오류: " + e.getMessage());
        }

        return new UserMypageResponse(
                new UserInfoDto(
                        user.getUserId(),
                        user.getUsername(),
                        user.getCreatedAt()
                ),
                submissions
        );
    }

}
