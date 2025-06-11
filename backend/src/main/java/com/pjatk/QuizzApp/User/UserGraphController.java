package com.pjatk.QuizzApp.User;

import com.pjatk.QuizzApp.Quiz.Quiz;
import com.pjatk.QuizzApp.Quiz.QuizService;
import com.pjatk.QuizzApp.User.DTO.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class UserGraphController
{
    private final UserService userService;
    private final QuizService quizService;


    @QueryMapping
    public UserDTO userById(@Argument int id)
    {
        return userService.getUserById(id);
    }

    @QueryMapping
    public List<UserDTO> users()
    {
        return userService.getAllUsers();
    }

//    @SchemaMapping(typeName = "User", field = "finishedQuizzes")
//    public Set<Quiz> finishedQuizzes(UserDTO user)
//    {
//        return userService.finishedByUser(user.getId());
//    }

    @SchemaMapping(typeName = "User", field = "quizzesCreated")
    public Set<Quiz> quizzesCreated(UserDTO user)
    {
        return quizService.getAllByAuthorId(user.getId());
    }


}
