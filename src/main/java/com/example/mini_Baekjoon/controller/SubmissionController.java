package com.example.mini_Baekjoon.controller;

import com.example.mini_Baekjoon.dto.Submission.SubmissionRequest;
import com.example.mini_Baekjoon.entity.Submission;
import com.example.mini_Baekjoon.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    /** 폼(WWW-FORM-URLENCODED) 전송 처리 */
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Submission> submitByForm(SubmissionRequest dto) {
        Submission saved = submissionService.saveSubmission(dto);
        return ResponseEntity.ok(saved);   // 저장 내용(JSON) 반환
    }

    /* 필요하면 JSON 전송(@RequestBody)용 메서드를 하나 더 둘 수 있습니다 */
}
