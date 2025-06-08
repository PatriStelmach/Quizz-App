package com.pjatk.QuizzApp.GraphQL;

import graphql.GraphQLContext;
import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;

import java.util.Locale;

public class Scalars
{
    public static final GraphQLScalarType INSTANT = GraphQLScalarType.newScalar()
            .name("INSTANT")
            .description("A current moment in time")
            .coercing(new Coercing()
            {
               @Override
               public Object serialize(Object dataFetcherResult, GraphQLContext context, Locale locale)
               {
                   return serializeInstant(dataFetcherResult);
               }

               @Override
                public Object parsevalue(Object input, GraphQLContext context, Locale locale)
               {
                   return parseInstantFromVariable(input);
               }

               @Override
                public Object parse
            })
}
