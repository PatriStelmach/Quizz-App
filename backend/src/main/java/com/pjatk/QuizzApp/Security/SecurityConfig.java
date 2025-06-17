package com.pjatk.QuizzApp.Security;

import com.pjatk.QuizzApp.Configuration.CustomOAuth2Service;
import com.pjatk.QuizzApp.Configuration.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig
{
    private final JwtFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final CustomOAuth2Service customOAuth2Service;
    private final OAuth2AuthenticationSuccessHandler oauth2LoginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {

       return http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                                        req.requestMatchers(
                                                        "/auth/**",
                                                        "/graphql/**",
                                                        "/graphiql/**",
                                                        "/oauth2/**",
                                                        "/login/oauth2/code/**",
                                                        "/swagger-resources",
                                                        "/swagger-resources/**",
                                                        "/configuration/ui",
                                                        "/configuration/security",
                                                        "/swagger-ui/**",
                                                        "/webjars/**",
                                                        "/quiz-ws/**",
                                                        "/rooms/**",
                                                        "/swagger-ui.html",
                                                        "/v3/api-docs/**"

                                                ).permitAll()
                                                .anyRequest()
                                                .authenticated()
                )
               .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .oauth2Login(oauth2 ->{
                   System.out.println("⚠️ OAuth2 Login config loaded!");
                       oauth2
                       .userInfoEndpoint(userInfo -> userInfo
                               .userAuthoritiesMapper(this.userAuthoritiesMapper())
                               .userService(customOAuth2Service)
                       )
                       .successHandler(oauth2LoginSuccessHandler);
               })
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();


    }

    private GrantedAuthoritiesMapper userAuthoritiesMapper()
    {
        return (authorities) ->
        {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            authorities.forEach(authority ->
            {
                if (authority instanceof OidcUserAuthority oidcUserAuthority)
                {

                    OidcIdToken idToken = oidcUserAuthority.getIdToken();
                    OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();

                    // Map the claims found in idToken and/or userInfo
                    // to one or more GrantedAuthority's and add it to mappedAuthorities

                } else if (authority instanceof OAuth2UserAuthority oauth2UserAuthority)
                {

                    Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();

                    // Map the attributes found in userAttributes
                    // to one or more GrantedAuthority's and add it to mappedAuthorities

                }
            });

            return mappedAuthorities;
        };
    }
}
