package com.pjatk.QuizzApp.Quiz;

import com.pjatk.QuizzApp.Question.Question;
import com.pjatk.QuizzApp.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
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
    private String category;
    private Diff diff;
    @Lob
    private byte[] image;
    private User author;
    private LocalDateTime timeLimit;
    private Integer maxPoints;
    private Set<Question> questions = new HashSet<>();

}
