package com.example.mini_Baekjoon.controller;

import com.example.mini_Baekjoon.dto.Baekjoon.BaekjoonDetailDto;
import com.example.mini_Baekjoon.dto.Baekjoon.BaekjoonListDto;
import com.example.mini_Baekjoon.dto.Problem.ProblemDetailDto;
import com.example.mini_Baekjoon.dto.Problem.ProblemSummaryDto;
import com.example.mini_Baekjoon.dto.Submission.SubmissionRequest;
import com.example.mini_Baekjoon.entity.Submission;
import com.example.mini_Baekjoon.service.BaekjoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Baekjoon")
public class ProblemsController {

    private final BaekjoonService baekjoonService;

    /** 2.1 문제 목록 조회 */
    @GetMapping
    public List<BaekjoonListDto> getAllProblems() {
        return baekjoonService.getList();
    }

    /** 2.2 문제 상세 조회 */
    @GetMapping("/{problemId}")
    public ResponseEntity<BaekjoonDetailDto> getProblemDetail(@PathVariable int problemId) {
        BaekjoonDetailDto problemDetail = baekjoonService.getDetail(problemId);  // 문제 상세 조회 서비스 메서드 호출
        if (problemDetail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(problemDetail);
    }

}
