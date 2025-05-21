package com.pjatk.QuizzApp.Quiz;

import com.pjatk.QuizzApp.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "quizzes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quiz
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message = "Title cannot be empty")
    @NotBlank(message = "Title cannot be blank")
    @Column(nullable = false)
    private String title;

    @NotEmpty(message = "Description cannot be empty")
    @NotBlank(message = "Description cannot be blank")
    @Column(nullable = false)
    private String description;

    @NotEmpty(message = "Category cannot be empty")
    @NotBlank(message = "Category cannot be blank")
    @Column(nullable = false)
    private String category;

    @NotEmpty(message = "Diff cannot be empty")
    @NotBlank(message = "Diff cannot be blank")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Diff diff;

    @NotEmpty(message = "Image cannot be empty")
    @NotBlank(message = "Image cannot be blank")
    @Column(nullable = false)
    private String image;

    @NotEmpty(message = "Author cannot be empty")
    @NotBlank(message = "Author cannot be blank")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    private Instant createdAt = Instant.now();

    @NotEmpty(message = "Time limit cannot be empty")
    @NotBlank(message = "Time limit cannot be blank")
    @Column(nullable = false)
    private LocalDateTime timeLimit;

    @Column(name = "finished")
    private boolean finished = false;

    @NotEmpty(message = "Max points cannot be empty")
    @NotBlank(message = "Max points cannot be blank")
    @Column(nullable = false)
    private Integer maxPoints;

    @Column(nullable = false)
    private Integer achievedPoints;


}
