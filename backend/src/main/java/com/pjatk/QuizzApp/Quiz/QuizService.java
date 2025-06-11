package com.pjatk.QuizzApp.Quiz;

import com.pjatk.QuizzApp.Exceptions.QuizNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuizService
{
    private final QuizRepository quizRepository;

    public Set<Quiz> getAllByAuthorId(Integer creatorId)
    {
        return quizRepository.getQuizzesByAuthorId(creatorId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found"));
    }

    public Quiz getById(Integer id)
    {
        return quizRepository.findById(id).orElseThrow(()->new QuizNotFoundException("Quiz not found"));
    }
}
