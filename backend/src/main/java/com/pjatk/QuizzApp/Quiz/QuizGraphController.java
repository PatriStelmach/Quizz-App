package com.pjatk.QuizzApp.Quiz;

import com.pjatk.QuizzApp.Exceptions.QuizNotFoundException;
import com.pjatk.QuizzApp.Question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Set;

@RequiredArgsConstructor
@Controller
public class QuizGraphController
{
    private final QuizService quizService;

    @QueryMapping
    public Quiz quizById(@Argument Integer id)
    {
       return quizService.getById(id);
    }

}
