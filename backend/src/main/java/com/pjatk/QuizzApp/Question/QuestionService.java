package com.pjatk.QuizzApp.Question;

import com.pjatk.QuizzApp.Answer.Answer;
import com.pjatk.QuizzApp.Answer.AnswerDTO;
import com.pjatk.QuizzApp.Configuration.Mapper;
import com.pjatk.QuizzApp.Exceptions.QuizNotFoundException;
import com.pjatk.QuizzApp.Exceptions.UserNotFoundException;
import com.pjatk.QuizzApp.Quiz.Quiz;
import com.pjatk.QuizzApp.Quiz.QuizRepository;
import com.pjatk.QuizzApp.User.User;
import com.pjatk.QuizzApp.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Transactional
public class QuestionService
{
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final Mapper mapper;

    public Integer createQuestion(QuestionDTO questionDTO) throws AccessDeniedException
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated())
        {
            userRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            Question question = new Question();
            question.setId(null);
            question.setQuestion(questionDTO.getQuestion());
            question.setQuiz(quizRepository.findById(questionDTO.getQuizId())
                    .orElseThrow(() -> new QuizNotFoundException("Quiz not found")));


            List<Answer> answers = new ArrayList<>();
            if (questionDTO.getAnswers() != null)
            {
                for (AnswerDTO answerDTO : questionDTO.getAnswers())
                {
                    Answer answer = new Answer();
                    answer.setId(null);
                    answer.setQuestion(question);
                    answer.setAnswerText(answerDTO.getAnswerText());
                    answer.setCorrect(answerDTO.isCorrect());

                    answers.add(answer);
                    System.out.println("Answer: " + answer);
                }
            }
            question.setAnswers(answers);


            return mapper.questionToDto(questionRepository.save(question)).getQuizId();
        }
        else
        {
            throw new AccessDeniedException("You don't a have permission to create new question");
        }
    }


}