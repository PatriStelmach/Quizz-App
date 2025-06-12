package com.pjatk.QuizzApp.Question;
import com.pjatk.QuizzApp.Quiz.Diff;
import com.pjatk.QuizzApp.Quiz.Quiz;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Question
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "Diff cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Diff diff;

    @NotNull(message = "Question Type cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType;

    @NotNull(message = "Question cannot be null")
    @Column(nullable = false)
    private String question;

    private String openAnswer;

    @NotNull(message = "Correct Answer cannot be null")
    @Column(nullable = false)
    private String correctAnswer;

    @NotNull(message = "Answers cannot be null")
    private Set<String> answers = new HashSet<>();

    @NotEmpty(message = "Quiz cannot be empty")
    @NotBlank(message = "Quiz cannot be blank")
    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;
}
