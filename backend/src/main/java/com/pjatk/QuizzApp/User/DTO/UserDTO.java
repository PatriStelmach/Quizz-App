package com.pjatk.QuizzApp.User.DTO;

import com.pjatk.QuizzApp.Quiz.Quiz;
import com.pjatk.QuizzApp.User.UserLevel;
import com.pjatk.QuizzApp.role.Role;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO
{
    private Integer id;
    private String username;
    private Set<Role> roles;
    private String email;
    private String avName;
    private String avType;
    private String avatar;
    private String bio;
    private boolean isActive;
    private boolean accountLocked;
    private boolean enabled;
    private Instant createdAt;
    private LocalDateTime updatedAt;
    private LocalDate birthDate;
    private UserLevel userLevel;
    private Integer streak;
    private Integer score;
    private Integer finished_amount;
    private Set<Quiz> finished_quizzes;
    private Set<Quiz> quizScore;
    private Set<Quiz> quizzesCreated;
}
