package com.pjatk.QuizzApp.Quiz;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserQuizScoreRepository extends JpaRepository<UserQuizScore, Integer>
{
    Optional<List<UserQuizScore>> findQuizScoresByUserId(Integer id);

    Optional<UserQuizScore>findByQuizId(Integer quizId);

    Optional<UserQuizScore> findByUserId(Integer id);

}
