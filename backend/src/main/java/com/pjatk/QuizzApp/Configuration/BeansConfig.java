package com.pjatk.QuizzApp.Configuration;

import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Base64;

@Configuration
@RequiredArgsConstructor
public class BeansConfig
{
    private final UserDetailsService userDetailsService;

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception
    {
         return config.getAuthenticationManager();
    }

    @Bean
    public ModelMapper modelMapper()
    {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return modelMapper;
    }

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder.scalar(byteArrayScalar());
    }

    @Bean
    public GraphQLScalarType byteArrayScalar() {
        return GraphQLScalarType.newScalar()
                .name("ByteArray")
                .description("Binary data")
                .coercing(new Coercing<byte[], String>() {
                    @Override
                    public String serialize(Object dataFetcherResult) {
                        return Base64.getEncoder().encodeToString((byte[]) dataFetcherResult);
                    }

                    @Override
                    public byte[] parseValue(Object input) {
                        return Base64.getDecoder().decode((String) input);
                    }

                    @Override
                    public byte[] parseLiteral(Object input) {
                        return parseValue(input);
                    }
                })
                .build();
    }


}
