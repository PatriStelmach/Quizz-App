package com.pjatk.QuizzApp.Configuration;

import com.pjatk.QuizzApp.User.User;
import com.pjatk.QuizzApp.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2Service extends DefaultOAuth2UserService
{

    private final UserRepository userRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("=== CustomOAuth2Service.loadUser() started ===");
        System.out.println("Client Registration ID: " + userRequest.getClientRegistration().getRegistrationId());

        OAuth2User oauth2User = super.loadUser(userRequest);
        System.out.println("OAuth2User loaded successfully");
        System.out.println("Attributes: " + oauth2User.getAttributes());

        try {
            OAuth2User result = processOAuth2User(oauth2User);
            System.out.println("=== CustomOAuth2Service.loadUser() completed ===");
            return result;
        } catch (Exception ex) {
            System.err.println("Error in processOAuth2User: " + ex.getMessage());
            ex.printStackTrace();
            throw new OAuth2AuthenticationException("Error processing OAuth2 user: " + ex.getMessage());
        }
    }

    private OAuth2User processOAuth2User(OAuth2User oauth2User)
    {
        System.out.println(11);
        Map<String, Object> attributes = oauth2User.getAttributes();

        String email = (String) attributes.get("email");
        String picture = (String) attributes.get("picture");

        if (email == null || email.isEmpty())
        {
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }

        // Znajdź lub stwórz użytkownika
        User user = userRepository.findByEmail(email)
                .orElse(createNewUser(email, picture));


        return new CustomOAuth2User(user, attributes);
    }

    private User createNewUser(String email, String picture) {
        try
        {
            User user = new User();
            user.setEmail(email);
            user.setUsername(email);
            user.setAvatar(picture.getBytes());
            user.setEnabled(true);
            return userRepository.save(user);
        }
        catch (DataIntegrityViolationException e)
        {

            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User creation failed"));
        }
    }


}
