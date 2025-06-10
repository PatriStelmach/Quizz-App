package com.pjatk.QuizzApp.User;

import com.pjatk.QuizzApp.User.DTO.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mapper
{
    private final ModelMapper modelMapper;

    public UserDTO toDto(User user)
    {
        return modelMapper.map(user, UserDTO.class);
    }

    public User toEntity(UserDTO userDto)
    {
        return modelMapper.map(userDto, User.class);
    }
}
