package com.pjatk.QuizzApp.Configuration;


import com.pjatk.QuizzApp.Security.JwtService;
import com.pjatk.QuizzApp.User.User;
import com.pjatk.QuizzApp.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        System.out.println("=== OAuth2AuthenticationSuccessHandler started ===");
        System.out.println("Authentication type: " + authentication.getClass().getSimpleName());

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oauthUser.getAttributes();

        System.out.println("OAuth2 attributes: " + attributes);

        String email      = (String) attributes.get("email");
        String pictureUrl = (String) attributes.get("picture");

        if (email == null || email.isEmpty())
        {
            throw new ServletException("Email not found from OAuth2 provider");
        }


        User user = userRepository.findByEmail(email)
                .map(existing ->
                {
                    existing.setUsername(email);
                    existing.setAvatar(pictureUrl.getBytes());
                    return userRepository.save(existing);
                })
                .orElseGet(() -> {
                    User u = new User();
                    u.setEmail(email);
                    u.setUsername(email);
                    u.setAvatar(pictureUrl.getBytes());
                    return userRepository.save(u);
                });

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        String jwtToken = jwtService.generateToken(claims, user);

        String redirectUrl = "http://localhost:5173/home?token=" + jwtToken;
        response.sendRedirect(redirectUrl);
    }
}