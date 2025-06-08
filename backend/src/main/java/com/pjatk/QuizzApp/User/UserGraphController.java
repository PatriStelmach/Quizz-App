package com.pjatk.QuizzApp.User;

import com.pjatk.QuizzApp.User.DTO.GetUserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserGraphController
{
//    private final UserService userService;
//
//    @QueryMapping
//    public User getById(@Argument int id)
//    {
//        return userService.getUserById(id);
//    }


    @QueryMapping
    public String hello()
    {
        return "Hello World";
    }


}
