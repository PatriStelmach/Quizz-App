package com.pjatk.QuizzApp.Question;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController
{
    private final QuestionService questionService;

    @PostMapping("/create")
    public ResponseEntity<?> createQuestion(@RequestBody QuestionDTO questionDTO)
    {
        try
        {
            return ResponseEntity.ok().body(questionService.createQuestion(questionDTO));
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
