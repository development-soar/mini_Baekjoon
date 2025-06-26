package com.example.mini_Baekjoon.service;

import com.example.mini_Baekjoon.dto.Problem.ProblemDetailDto;
import com.example.mini_Baekjoon.dto.Problem.ProblemSummaryDto;
import com.example.mini_Baekjoon.entity.Baekjoon;
import com.example.mini_Baekjoon.repository.BaekjoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaekjoonService {

    private final BaekjoonRepository baekjoonRepository;

    public List<ProblemSummaryDto> getList() {
        return baekjoonRepository.findAll().stream()
                .map(b -> new ProblemSummaryDto(
                        b.getBaekjoonId(),
                        b.getTitle(),
                        Optional.ofNullable(b.getAlgorithmCategory()).orElse("")
                ))
                .toList();
    }

    public ProblemDetailDto getDetail(int id) {
        Baekjoon b = baekjoonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("문제 없음: " + id));

        return new ProblemDetailDto(
                b.getBaekjoonId(),
                b.getTitle(),
                b.getDescription(),
                b.getInputDescription(),
                b.getOutputDescription(),
                b.getAnswerCode(),
                Optional.ofNullable(b.getAlgorithmCategory()).orElse("")
        );
    }
}
