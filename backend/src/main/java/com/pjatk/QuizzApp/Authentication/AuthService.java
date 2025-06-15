package com.pjatk.QuizzApp.Authentication;

import com.pjatk.QuizzApp.Exceptions.TokenNotFoundException;
import com.pjatk.QuizzApp.Exceptions.UserNotFoundException;
import com.pjatk.QuizzApp.Security.JwtService;
import com.pjatk.QuizzApp.User.Token;
import com.pjatk.QuizzApp.User.TokenRepository;
import com.pjatk.QuizzApp.User.User;
import com.pjatk.QuizzApp.User.UserRepository;
import com.pjatk.QuizzApp.email.EmailService;
import com.pjatk.QuizzApp.email.EmailTemplateName;
import com.pjatk.QuizzApp.role.RoleRepository;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService
{
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    public void register(RegistrationRequest request) throws MessagingException
    {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initialized"));

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(Set.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);


    }

    private void sendValidationEmail(User user) throws MessagingException
    {
        var newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.getUsername(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );

    }

    private String generateAndSaveActivationToken(User user)
    {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(Instant.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length)
    {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            codeBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return codeBuilder.toString();
    }

    public AuthResponse authenticate(@Valid AuthRequest request)
    {
        String usernameOrEmail = request.getIdentifier();
        if(usernameOrEmail.contains("@"))
        {
            User user = userRepository.findByEmail(usernameOrEmail).orElseThrow(() ->
                    new UserNotFoundException("User not found with given email."));
            usernameOrEmail = user.getUsername();
        }
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                     usernameOrEmail,
                     request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((User)auth.getPrincipal());
        claims.put("username", user.getUsername());
        var jwtToken = jwtService.generateToken(claims, user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse loginAfterActivate(@Valid String token)
    {
        User userByToken = tokenRepository.findByToken(token).orElseThrow(() ->
                new UserNotFoundException("Account not activated yet")).getUser();

        var claims = new HashMap<String, Object>();
        claims.put("username", userByToken.getUsername());
        String jwtToken = jwtService.generateToken(claims, userByToken);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void activateAccount(String token) throws TokenNotFoundException, MessagingException
    {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Token not found"));
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt()))
        {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Token expired. A new token has been sent to your email.");
        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() ->  new UserNotFoundException(
                        "User not found with id: " + savedToken.getUser().getId()));
         user.setEnabled(true);
         userRepository.save(savedToken.getUser());
         savedToken.setValidatedAt(LocalDateTime.now());
         tokenRepository.save(savedToken);
    }
}
