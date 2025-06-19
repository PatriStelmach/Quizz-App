package com.pjatk.QuizzApp.Answer;

import com.pjatk.QuizzApp.Question.QuestionDTO;
import lombok.Data;

@Data
public class AnswerDTO
{
    private String answerText;;
    private boolean correct;
}