package com.pjatk.QuizzApp.Quiz;

import com.pjatk.QuizzApp.Question.Question;
import com.pjatk.QuizzApp.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
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


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Diff diff;

    @Lob
    private byte[] image;


    @ManyToOne
    private User author;

    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private LocalDateTime timeLimit;

    @Column(nullable = false)
    private Integer maxPoints;

    @OneToMany(mappedBy = "quiz")
    private Set<Question> questions = new HashSet<>();


}
