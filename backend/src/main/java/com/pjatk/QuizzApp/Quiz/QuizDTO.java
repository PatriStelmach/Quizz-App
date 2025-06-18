package com.pjatk.QuizzApp.Quiz;

import com.pjatk.QuizzApp.Question.Question;
import com.pjatk.QuizzApp.Question.QuestionDTO;
import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDTO
{
    private Integer id;
    private String title;
    private String description;
    private Category category;
    private Diff diff;
    private Integer maxPoints;
    private byte[] image;
    private Duration timeLimit;
    private List<QuestionDTO> questions;
}
