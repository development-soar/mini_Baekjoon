// src/main/java/com/example/mini_Baekjoon/controller/ProblemsViewController.java
package com.example.mini_Baekjoon.controller;

import com.example.mini_Baekjoon.dto.Problem.ProblemDetailDto;
import com.example.mini_Baekjoon.dto.Problem.ProblemSummaryDto;
import com.example.mini_Baekjoon.service.BaekjoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/problems")          // ① http://localhost:8080/problems
public class ProblemsViewController {

    private final BaekjoonService service;

    // 목록
    @GetMapping
    public String list(Model model) {
        List<ProblemSummaryDto> list = service.getList();
        model.addAttribute("problems", list);   // 템플릿으로 전달
        return "problems";                     // templates/problems.html
    }

    // 상세
    @GetMapping("/{id}")
    public String detail(@PathVariable int id, Model model) {
        ProblemDetailDto dto = service.getDetail(id);
        model.addAttribute("problem", dto);     // problem만 넣으면 목록은 null
        return "problems";                      // 똑같은 템플릿 재사용
    }

}
