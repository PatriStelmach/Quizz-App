package com.pjatk.QuizzApp.Quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateQuizRequest
{
    private String title;
    private String description;
    private String category;
    private Diff diff;
    private Integer maxPoints;
}
