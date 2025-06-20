package com.pjatk.QuizzApp.Quiz;

import com.pjatk.QuizzApp.Question.QuestionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolvedDto
{
    private String title;
    private Category category;
    private Diff diff;
    private Integer maxPoints;
    private Integer score;
}

