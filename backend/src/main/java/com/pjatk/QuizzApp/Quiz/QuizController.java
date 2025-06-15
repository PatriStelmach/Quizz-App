package com.pjatk.QuizzApp.Quiz;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizservice;

        @PostMapping("/create")
        public ResponseEntity<?> createQuiz(@RequestParam String title,
                                            @RequestParam String description,
                                            @RequestParam String category,
                                            @RequestParam Diff diff,
                                            @RequestParam Integer maxPoints
        )
        {
            try
            {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(quizservice.createQuiz(QuizDTO.builder()
                                .title(title)
                                .description(description)
                                .diff(diff)
                                .category(category)
                                .maxPoints(maxPoints)
                                .build()));
            }
            catch (Exception e)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }

        }
    
}
