package com.pjatk.QuizzApp.User;

import com.pjatk.QuizzApp.Quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>
{
    Optional<User> findById(Integer id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

}
