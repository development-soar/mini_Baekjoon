package com.example.mini_Baekjoon.controller;

import com.example.mini_Baekjoon.dto.Baekjoon.BaekjoonDetailDto;
import com.example.mini_Baekjoon.dto.Baekjoon.BaekjoonListDto;
import com.example.mini_Baekjoon.dto.Problem.ProblemDetailDto;
import com.example.mini_Baekjoon.service.BaekjoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/problems")
@RequiredArgsConstructor
public class ProblemsViewController {

    private final BaekjoonService baekjoonService;

    @GetMapping
    public List<BaekjoonListDto> getAllProblems() {
        return baekjoonService.getList();      // ← 서비스 메서드명·DTO 맞춤
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaekjoonDetailDto> getProblemDetail(@PathVariable Integer id) {
        BaekjoonDetailDto detail = baekjoonService.getDetail(id);
        return detail == null ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(detail);
    }

}