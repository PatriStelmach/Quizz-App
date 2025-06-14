package com.pjatk.QuizzApp.User;

import com.pjatk.QuizzApp.User.DTO.UserDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
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
   }
    public UserDTO toDto(User user)
    {
        return modelMapper.map(user, UserDTO.class);
    }

    public void toEntity(UserDTO userDto , User user)
    {
         modelMapper.map(userDto, user);
    }
}
