package com.pjatk.QuizzApp.User;

import com.pjatk.QuizzApp.Exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class Resolver
{
    private final UserRepository userRepository;

    @QueryMapping
    public User user(@Argument Integer id)
    {
        return userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User with id:"+id+" not found"));
    }

    @QueryMapping
    public List<User> users()
    {
        return userRepository.findAll();
    }
}
