package ex.QuizzApp.Security;

import ex.QuizzApp.Exceptions.UserNotFoundException;
import ex.QuizzApp.User.User;
import ex.QuizzApp.User.UserRepository;
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
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException
    {
      return userRepository.findByEmail(email)
              .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }
}
