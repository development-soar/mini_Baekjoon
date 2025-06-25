package com.example.mini_Baekjoon.service;

import com.example.mini_Baekjoon.dto.Baekjoon.BaekjoonDetailDto;
import com.example.mini_Baekjoon.dto.Baekjoon.BaekjoonListDto;
import com.example.mini_Baekjoon.dto.Problem.ProblemDetailDto;
import com.example.mini_Baekjoon.dto.Problem.ProblemSummaryDto;
import com.example.mini_Baekjoon.entity.Baekjoon;
import com.example.mini_Baekjoon.repository.BaekjoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BaekjoonService {

    private final BaekjoonRepository baekjoonRepository;

    public List<BaekjoonListDto> getList() {
        return baekjoonRepository.findAll().stream()
                .map(b -> new BaekjoonListDto(
                        b.getBaekjoonId(),
                        b.getTitle(),
                        b.getAlgorithmCategory()))
                .toList();
    }

    public BaekjoonDetailDto getDetail(Integer id) {
        Baekjoon b = baekjoonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("문제 없음 id=" + id));
        return new BaekjoonDetailDto(
                b.getBaekjoonId(),
                b.getTitle(),
                b.getDescription(),
                b.getInputDescription(),
                b.getOutputDescription(),
                b.getAnswerCode(),
                b.getAlgorithmCategory()
        );
    }
}


/*
public class BaekjoonService {

    private final List<ProblemDetailDto> problems = new ArrayList<>();

    public BaekjoonService() {
        problems.add(new ProblemDetailDto(
                1000,
                "A+B 문제",
                "두 수를 입력받아 합을 출력하세요",
                "첫째 줄에 A와 B가 주어진다.",
                "첫째 줄에 A+B의 결과를 출력한다.",
                "a, b = map(int, input().split())\nprint(a + b)",
                "수학"
        ));
        // 문제 추가 가능
    }

    public List<ProblemSummaryDto> getAllProblems() {
        List<ProblemSummaryDto> summaries = new ArrayList<>();
        for (ProblemDetailDto p : problems) {
            summaries.add(new ProblemSummaryDto(
                    p.getBaekjoonId(),
                    p.getTitle(),
                    p.getAlgorithmCategory()
            ));
        }
        return summaries;
    }

    public ProblemDetailDto getProblemById(int problemId) {
        return problems.stream()
                .filter(p -> p.getBaekjoonId() == problemId)
                .findFirst()
                .orElse(null);
    }
}
*/