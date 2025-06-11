package com.pjatk.QuizzApp.Quiz;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface QuizRepository extends JpaRepository<Quiz, Integer>
{
    Optional<Set<Quiz>> getQuizzesByAuthorId(Integer id);
}
