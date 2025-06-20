package com.pjatk.QuizzApp.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Query("SELECT q FROM Question q JOIN FETCH q.answers WHERE q.quiz.id = :quizId")
    List<Question> findByQuizId(@Param("quizId") Integer quizId);
}
