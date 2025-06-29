package com.pjatk.QuizzApp.User;

import com.pjatk.QuizzApp.Quiz.Quiz;
import com.pjatk.QuizzApp.Quiz.QuizService;
import com.pjatk.QuizzApp.User.DTO.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class UserGraphController
{
    private final UserService userService;
    private final QuizService quizService;


    @QueryMapping
    public UserDTO userById(@Argument String username)
    {
        return userService.getUserById(username);
    }

    @QueryMapping
    public List<UserDTO> allUsers()
    {
        return userService.getAllUsers();
    }


    @SchemaMapping(typeName = "User", field = "quizzesCreated")
    public Set<Quiz> quizzesCreated(UserDTO user)
    {
        return quizService.getAllByAuthorId(user.getId());
    }

    @MutationMapping
    public UserDTO updateUser(@Argument String username, @Argument UserDTO input) throws AccessDeniedException
    {
        return userService.updateUser(username, input);
    }



}
