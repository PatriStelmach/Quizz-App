package com.pjatk.QuizzApp.Quiz;

import com.pjatk.QuizzApp.Answer.Answer;
import com.pjatk.QuizzApp.Answer.AnswerDTO;
import com.pjatk.QuizzApp.Answer.AnswerRepository;
import com.pjatk.QuizzApp.Configuration.Mapper;
import com.pjatk.QuizzApp.Exceptions.QuizNotFoundException;
import com.pjatk.QuizzApp.Exceptions.UserNotFoundException;
import com.pjatk.QuizzApp.Question.Question;
import com.pjatk.QuizzApp.Question.QuestionDTO;
import com.pjatk.QuizzApp.Question.QuestionRepository;
import com.pjatk.QuizzApp.User.User;
import com.pjatk.QuizzApp.User.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService
{
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;
    private final UserQuizScoreRepository uqRepository;

    public Set<Quiz> getAllByAuthorId(Integer creatorId)
    {
        return quizRepository.getQuizzesByAuthorId(creatorId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found"));
    }

    public Quiz getById(Integer id)
    {
        return quizRepository.findById(id).orElseThrow(()->new QuizNotFoundException("Quiz not found"));
    }


    public Integer createQuiz(QuizDTO quizDTO) throws AccessDeniedException
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated())
        {
            User author = userRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            Quiz quiz = new Quiz();
            mapper.quizDTOToEntity(quizDTO, quiz);
            quiz.setAuthor(author);

//            Set<Question> questions = new HashSet<>();
//
//            if (quizDTO.getQuestions() != null)
//            {
//                for (QuestionDTO questionDTO : quizDTO.getQuestions())
//                {
//                    Question question = new Question();
//                    mapper.questionDTOToEntity(questionDTO, question);
//                    question.setQuiz(quiz);
//
//                    Set<Answer> answers = new HashSet<>();
//                    if (questionDTO.getAnswers() != null)
//                    {
//                        for (AnswerDTO answerDTO : questionDTO.getAnswers())
//                        {
//                            Answer answer = new Answer();
//                            mapper.answerDTOToEntity(answerDTO, answer);
//                            answer.setQuestion(question);
//                            answers.add(answer);
//                        }
//                    }
//                    question.setAnswers(answers);
//                    questions.add(question);
//                }
//            }
//
//            quiz.setQuestions(questions);

             mapper.quizToDto(quizRepository.save(quiz));
             return quiz.getId();

        } else {
            throw new AccessDeniedException("You don't have permission to create new quiz");
        }
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

    public List<Quiz> getAll()
    {
        return quizRepository.findAll();
    }

    public List<SolvedDto> getUserSolved(String username)
    {
        int userId = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"))
                .getId();

        List<UserQuizScore> scores = uqRepository.findByUserId(userId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found"));

        return scores.stream()
                .map(score ->
                        new SolvedDto(
                                score.getQuiz().getTitle(),
                                score.getQuiz().getCategory(),
                                score.getQuiz().getDiff(),
                                score.getQuiz().getMaxPoints(),
                                score.getScore())).toList();
    }
}
