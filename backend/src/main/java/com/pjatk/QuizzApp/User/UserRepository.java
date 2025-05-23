package com.pjatk.QuizzApp.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Integer>
{
    Optional<User> findById(Integer id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    Set<User> getAllById(Integer id);

    Set<User> getAllByUsername(String username);
}
