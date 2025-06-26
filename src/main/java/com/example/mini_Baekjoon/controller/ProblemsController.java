package com.example.mini_Baekjoon.controller;

import com.example.mini_Baekjoon.dto.Problem.ProblemDetailDto;
import com.example.mini_Baekjoon.dto.Problem.ProblemSummaryDto;
import com.example.mini_Baekjoon.service.BaekjoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Baekjoon")
public class ProblemsController {

    private final BaekjoonService baekjoonService;

    @GetMapping
    public List<ProblemSummaryDto> getAllProblems() {
        return baekjoonService.getList();
    }

    @GetMapping("/{problemId}")
    public ResponseEntity<ProblemDetailDto> getProblemDetail(@PathVariable int problemId) {
        ProblemDetailDto problemDetail = baekjoonService.getDetail(problemId);
        return ResponseEntity.ok(problemDetail);
    }

}
