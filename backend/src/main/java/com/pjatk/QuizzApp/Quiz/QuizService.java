package com.pjatk.QuizzApp.Quiz;

import com.pjatk.QuizzApp.Configuration.Mapper;
import com.pjatk.QuizzApp.Exceptions.QuizNotFoundException;
import com.pjatk.QuizzApp.Exceptions.UserNotFoundException;
import com.pjatk.QuizzApp.User.User;
import com.pjatk.QuizzApp.User.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuizService
{
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;

    public Set<Quiz> getAllByAuthorId(Integer creatorId)
    {
        return quizRepository.getQuizzesByAuthorId(creatorId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found"));
    }

    public Quiz getById(Integer id)
    {
        return quizRepository.findById(id).orElseThrow(()->new QuizNotFoundException("Quiz not found"));
    }

    @Transactional
    public QuizDTO createQuiz(QuizDTO quizDTO) throws AccessDeniedException
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Quiz quiz = new Quiz();

        if(authentication != null && authentication.isAuthenticated())
        {
            int userId = userRepository.findByUsername(authentication.getName()).orElseThrow(() ->
                                        new UserNotFoundException("User not found")).getId();
            quiz = mapper.quizDTOToEntity(quizDTO, quiz);
            quiz.setAuthor(userRepository.findById(userId).orElseThrow(() ->
                                        new UserNotFoundException("User not found")));


        }
        else{
            throw new AccessDeniedException("You don't have permission to create new quiz");
        }



        return mapper.quizToDto(quizRepository.save(quiz));
    }

    public QuizDTO updateQuiz(Integer id, QuizDTO quizDTO) throws AccessDeniedException
    {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated())
        {

            int userId = userRepository
                    .findByUsername(authentication
                            .getName())
                    .orElseThrow(() ->
                    new UserNotFoundException("User not found"))
                    .getId();
            int AuthorId = quiz.getAuthor().getId();

            boolean isAdmin = authentication.getAuthorities()
                    .stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
            if(isAdmin || userId == AuthorId)
            {
                mapper.quizDTOToEntity(quizDTO, quiz);
            }
            else
            {
                throw new AccessDeniedException("You don't have permission to update this quiz");
            }
        }


        return mapper.quizToDto(quizRepository.save(quiz));
    }
}
