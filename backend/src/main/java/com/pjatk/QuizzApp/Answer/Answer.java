package com.pjatk.QuizzApp.Answer;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pjatk.QuizzApp.Question.Question;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class Answer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private boolean correct;

    @Column(nullable = false)
    private String answerText;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;



}
