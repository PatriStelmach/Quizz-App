package com.pjatk.QuizzApp.Configuration;

import com.pjatk.QuizzApp.Quiz.Quiz;
import com.pjatk.QuizzApp.Quiz.QuizDTO;
import com.pjatk.QuizzApp.User.DTO.UserDTO;
import com.pjatk.QuizzApp.User.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mapper
{
    private final ModelMapper modelMapper;

   @PostConstruct
   public void init()
   {
       modelMapper.createTypeMap(User.class, UserDTO.class).setPropertyCondition(Conditions.isNotNull());
       modelMapper.createTypeMap(Quiz.class, QuizDTO.class).setPropertyCondition(Conditions.isNotNull());
   }

    public QuizDTO quizToDto(Quiz quiz)
    {
        return modelMapper.map(quiz, QuizDTO.class);
    }

    public void quizDTOToEntity(QuizDTO quizDto , Quiz quiz)
    {
        modelMapper.map(quizDto, quiz);
    }

    public UserDTO toDto(User user)
    {
        return modelMapper.map(user, UserDTO.class);
    }

    public void userDTOToEntity(UserDTO userDto , User user)
    {
         modelMapper.map(userDto, user);
    }
}
