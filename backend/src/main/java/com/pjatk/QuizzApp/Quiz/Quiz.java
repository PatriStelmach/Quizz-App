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

    @NotEmpty(message = "Diff cannot be empty")
    @NotBlank(message = "Diff cannot be blank")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Diff diff;

    @NotEmpty(message = "Image cannot be empty")
    @NotBlank(message = "Image cannot be blank")
    private String image;

    @NotEmpty(message = "Author cannot be empty")
    @NotBlank(message = "Author cannot be blank")
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    private Instant createdAt = Instant.now();

    @NotEmpty(message = "Time limit cannot be empty")
    @NotBlank(message = "Time limit cannot be blank")
    @Column(nullable = false)
    private LocalDateTime timeLimit;



    @NotNull
    @Column(nullable = false)
    private Integer maxPoints;

    @OneToMany(mappedBy = "quiz")
    @Column(nullable = false)
    private Set<Question> questions = new HashSet<>();


}
