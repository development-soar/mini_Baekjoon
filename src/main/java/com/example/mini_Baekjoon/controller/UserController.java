package com.example.mini_Baekjoon.controller;

import com.example.mini_Baekjoon.config.security.jwt.JwtTokenProvider;
import com.example.mini_Baekjoon.dto.User.UserInfoDto;
import com.example.mini_Baekjoon.dto.User.UserLoginRequest;
import com.example.mini_Baekjoon.dto.User.UserMypageResponse;
import com.example.mini_Baekjoon.dto.User.UserSignupRequest;
import com.example.mini_Baekjoon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserSignupRequest request) {
        try {
            userService.signup(request);
            return ResponseEntity.ok("회원가입 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("서버 오류: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/mypage")
    public ResponseEntity<?> getMyPage(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        String token = authHeader.substring(7);
        if (token.isEmpty()) {
            return ResponseEntity.status(401).body("토큰이 유효하지 않습니다.");
        }

        try {
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(401).body("토큰이 만료되었거나 유효하지 않습니다.");
            }

            String userId = jwtTokenProvider.getUserId(token);
            if (userId == null || userId.isEmpty()) {
                return ResponseEntity.status(401).body("사용자 정보를 추출할 수 없습니다.");
            }

            UserMypageResponse response = userService.getMyPage(userId);

            if (response == null) {
                response = new UserMypageResponse(
                        new UserInfoDto("", "", LocalDateTime.now()),
                        Collections.emptyList()
                );
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of(
                            "error", "서버 오류",
                            "message", e.getMessage()
                    )
            );
        }
    }
}
