package com.example.mini_Baekjoon.controller;

import com.example.mini_Baekjoon.dto.Submission.SubmissionRequest;
import com.example.mini_Baekjoon.entity.Submission;
import com.example.mini_Baekjoon.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> submit(@RequestBody SubmissionRequest dto,
                                    Principal principal) {

        String userId = principal.getName();

        Submission saved = submissionService.saveSubmission(userId, dto);

        return ResponseEntity.ok().body("{\"result\":\"correct\"}");
    }

    @PostMapping
    public String submitFromForm(SubmissionRequest dto, Principal principal) {
        String userId = (principal != null) ? principal.getName() : "testUser";
        submissionService.saveSubmission(userId, dto);
        return "submission-success";
    }
}
