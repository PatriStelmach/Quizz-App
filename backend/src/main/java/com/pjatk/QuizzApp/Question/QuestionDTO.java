package com.pjatk.QuizzApp.Question;

import com.pjatk.QuizzApp.Answer.AnswerDTO;
import lombok.Data;

import java.util.Set;

@Data
public class QuestionDTO {
    private String question;
    private Integer point;
    private QuestionType questionType;
    private Set<AnswerDTO> answers;
}