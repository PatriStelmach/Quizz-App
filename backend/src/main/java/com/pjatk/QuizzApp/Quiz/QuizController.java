package com.pjatk.QuizzApp.Quiz;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizservice;

        @PostMapping("/create")
    public ResponseEntity<?> createQuiz(@RequestBody QuizDTO quizDTO) throws AccessDeniedException{

        quizservice.createQuiz(quizDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(quizDTO);

    }
    
}
