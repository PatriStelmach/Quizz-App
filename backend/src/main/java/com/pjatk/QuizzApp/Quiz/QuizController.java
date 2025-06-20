package com.pjatk.QuizzApp.Quiz;

import java.nio.file.AccessDeniedException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjatk.QuizzApp.Question.Question;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizservice;
    private final QuizRepository quizRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createQuiz(@RequestBody QuizDTO quizDTO)
    {
        try
        {
            return ResponseEntity.status(HttpStatus.CREATED)
                        .body(quizservice.createQuiz(quizDTO));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/get-solved/{username}")
    public ResponseEntity<?> userSolvedQuizes(@PathVariable String username)
    {
        try
        {
            return ResponseEntity.ok().body(quizservice.getUserSolved(username));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable int id)
    {
        try
        {
            quizRepository.deleteById(id);
            return ResponseEntity.ok().body("Quiz deleted successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
}
