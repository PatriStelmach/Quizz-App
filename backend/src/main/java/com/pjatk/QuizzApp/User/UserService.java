package com.pjatk.QuizzApp.User;

import com.pjatk.QuizzApp.Exceptions.UserExistsException;
import com.pjatk.QuizzApp.Exceptions.UserNotFoundException;
import com.pjatk.QuizzApp.Exceptions.WrongPasswordException;
import com.pjatk.QuizzApp.Quiz.Quiz;
import com.pjatk.QuizzApp.User.DTO.UserDTO;
import com.pjatk.QuizzApp.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final Mapper mapper;


    public UserDTO getUserById(Integer id)
    {
        return userRepository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public String getLoggedUsername()
    {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public User getUserByUsername(String username)
    {
        return userRepository.findByUsername(username).orElseThrow(()
                -> new UserNotFoundException("User not found with username: " + username));
    }

    public User getUserByEmail(String email)
    {
        return userRepository.findByEmail(email).orElseThrow(()
                -> new UserNotFoundException("User not found with email: " + email));
    }

    public List<UserDTO> getAllUsers()
    {
        return userRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDTO updateUser(String username, UserDTO userDTO) throws AccessDeniedException
    {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + username));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated())
        {
            String currentUsername = authentication.getName();
            String profileUsername = user.getUsername();

            boolean isAdmin = authentication.getAuthorities()
                    .stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
            if(isAdmin || currentUsername.equals(profileUsername))
            {
                mapper.toEntity(userDTO, user);
                user.setUpdatedAt(LocalDateTime.now());
            }
            else
            {
                throw new AccessDeniedException("You don't have permission to update this user");
            }
        }


        return mapper.toDto(userRepository.save(user));
    }

    public String changeUsername(String username, String newUsername)
    {
        if(userRepository.findByUsername(username).isPresent())
        {
            throw new UserExistsException("User with given credentials already exists");
        }

        User user = getUserByUsername(username);
        user.setUsername(newUsername);
        return userRepository.save(user).getUsername();
    }

    public void changePassword(String username, String password, String newPass)
    {
        User user = getUserByUsername(username);
        if(!passwordEncoder.matches(password, user.getPassword()))
        {
            throw new WrongPasswordException("Wrong password!");
        }
        newPass = passwordEncoder.encode(newPass);
        user.setPassword(newPass);
        userRepository.save(user);
    }

    public void changeBio(String username, String bio)
    {
        User user = getUserByUsername(username);
        if(bio.length() < 255)
        {
            user.setBio(bio);
            userRepository.save(user);
        }
        else
        {
            System.out.println("bio is too long");
        }
    }

    public void changeAvatar(String username, MultipartFile avatar) throws IOException
    {
        if(avatar.getSize() > 2_000_000)
        {
            throw new InvalidObjectException("The image is too big");
        }
        User user = getUserByUsername(username);
        user.setAvName(avatar.getOriginalFilename());
        user.setAvType(avatar.getContentType());
        user.setAvatar(avatar.getBytes());

        userRepository.save(user);
    }

    public byte[] getAvatar(String username) throws UserNotFoundException
    {
        if(userRepository.findByUsername(username).isEmpty())
        {
            throw new UserNotFoundException("User with given username not found: " + username);
        }
        return userRepository.findByUsername(username).get().getAvatar();

    }

    public String addRole(String username, String roleName)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = getUserByUsername(username);
        if(authentication != null && authentication.isAuthenticated())
        {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            if(authorities
                    .stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ADMIN"))
            )
            {
                user.addRole(roleRepository.findByName(roleName).orElseThrow(()
                        -> new IllegalStateException("Role not found")));
            }
        }
        return "Roles of: " + username + '\n' + user.getRoles().toString() + '\n' ;
    }

    public String deleteRole(String username, String roleName)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = getUserByUsername(username);
        if(authentication != null && authentication.isAuthenticated())
        {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            if(authorities
                    .stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ADMIN"))
            )
            {
                user.deleteRole(roleRepository.findByName(roleName).orElseThrow(()
                        -> new IllegalStateException("Role not found")));
            }
        }
        return "Roles of: " + username + '\n' + user.getRoles().toString() + '\n' ;
    }
}
