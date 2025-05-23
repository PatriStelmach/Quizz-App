package com.pjatk.QuizzApp.Quiz;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizService
{
    private final QuizRepository quizRepository;
}
