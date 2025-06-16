package com.pjatk.QuizzApp.Configuration;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
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

import java.time.Duration;
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
        return wiringBuilder -> wiringBuilder.scalar(byteArrayScalar()).scalar(durationScalar());
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

    @Bean
    public GraphQLScalarType durationScalar()
    {
        return GraphQLScalarType.newScalar()
            .name("Duration")
                .description("A duration scalar that handles java.time.Duration in ISO-8601 format")
                .coercing(new Coercing<Duration, String>() {
                    @Override
                    public String serialize(Object dataFetcherResult) {
                        return ((Duration) dataFetcherResult).toString(); // e.g., PT30M
                    }

                    @Override
                    public Duration parseValue(Object input) {
                        return Duration.parse(input.toString());
                    }

                    @Override
                    public Duration parseLiteral(Object input) {
                        if (input instanceof graphql.language.StringValue) {
                            return Duration.parse(((graphql.language.StringValue) input).getValue());
                        }
                        throw new CoercingParseLiteralException("Expected ISO-8601 duration string.");
                    }
                })
                .build();

    }
}
