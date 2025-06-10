package com.pjatk.QuizzApp.User;

import com.pjatk.QuizzApp.User.DTO.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class UserGraphController
{
    private final UserService userService;
    @QueryMapping
    public UserDTO userById(@Argument int id)
    {
        return userService.getUserById(id);
    }


}
