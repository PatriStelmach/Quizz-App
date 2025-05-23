package com.pjatk.QuizzApp.Security;

import com.pjatk.QuizzApp.Exceptions.UserNotFoundException;
import com.pjatk.QuizzApp.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService
{
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException
    {
      return userRepository.findByUsername(username)
              .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }
}
