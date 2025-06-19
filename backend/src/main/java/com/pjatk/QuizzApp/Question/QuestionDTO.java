package com.pjatk.QuizzApp.Question;

import com.pjatk.QuizzApp.Answer.AnswerDTO;
import com.pjatk.QuizzApp.Quiz.Quiz;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class QuestionDTO
{
    private String question;
    private List<AnswerDTO> answers;
    private Integer quizId;
}
