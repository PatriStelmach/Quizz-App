package com.pjatk.QuizzApp.GraphQL;

import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;
import io.micrometer.common.lang.NonNull;

import java.time.Instant;
import java.time.LocalDateTime;

public class Scalars
{

    public static final GraphQLScalarType INSTANT = GraphQLScalarType.newScalar()
            .name("INSTANT")
            .description("A current moment in time")
            .coercing(new Coercing<Instant, String>()
            {
               @Override
               public String serialize(@NonNull Object input)
               {
                   return input.toString();
               }

                @Override
                public Instant parseValue(Object input) {
                    return Instant.parse(input.toString());
                }

               @Override
                public Instant parseLiteral(Object input)
               {
                   return Instant.parse(input.toString());
               }
            })
            .build();

    public static final GraphQLScalarType LOCALDATETIME = GraphQLScalarType.newScalar()
            .name("LocalDateTime")
            .description("A representation of date")
            .coercing(new Coercing<LocalDateTime, String>()
            {
                @Override
                public String serialize(@NonNull Object input)
                {
                    return input.toString();
                }

                @Override
                public LocalDateTime parseValue(Object input) {
                    return LocalDateTime.parse(input.toString());
                }

                @Override
                public LocalDateTime parseLiteral(Object input)
                {
                    return LocalDateTime.parse(input.toString());
                }
            })
            .build();
}
