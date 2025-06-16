package com.pjatk.QuizzApp.Quiz;

import com.pjatk.QuizzApp.Question.Question;
import com.pjatk.QuizzApp.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quiz
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    private String category;

    @Enumerated(EnumType.STRING)
    private Diff diff;

    @Lob
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    private Instant createdAt = Instant.now();

    private Duration timeLimit;

    private Integer maxPoints;

    @OneToMany(mappedBy = "quiz")
    private Set<Question> questions = new HashSet<>();


}
