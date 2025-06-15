package com.pjatk.QuizzApp.Quiz;

import com.pjatk.QuizzApp.Exceptions.QuizNotFoundException;
import com.pjatk.QuizzApp.Question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.nio.file.AccessDeniedException;
import java.util.Base64;
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

    @MutationMapping
    public QuizDTO updateQuiz(@Argument Integer id, @Argument QuizDTO input) throws AccessDeniedException
    {
        return quizService.updateQuiz(id, input);
    }

}
