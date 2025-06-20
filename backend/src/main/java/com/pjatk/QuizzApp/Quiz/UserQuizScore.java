package com.pjatk.QuizzApp.Quiz;

import com.pjatk.QuizzApp.User.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_quiz_scores")
public class UserQuizScore
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer score;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

