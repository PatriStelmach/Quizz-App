/* eslint-disable */
import * as types from './graphql';
import type{ TypedDocumentNode as DocumentNode } from '@graphql-typed-document-node/core';

/**
 * Map of all GraphQL operations in the project.
 *
 * This map has several performance disadvantages:
 * 1. It is not tree-shakeable, so it will include all operations in the project.
 * 2. It is not minifiable, so the string of a GraphQL query will be multiple times inside the bundle.
 * 3. It does not support dead code elimination, so it will add unused operations.
 *
 * Therefore it is highly recommended to use the babel or swc plugin for production.
 * Learn more about it here: https://the-guild.dev/graphql/codegen/plugins/presets/preset-client#reducing-bundle-size
 */
type Documents = {
    "query AllQuizzes {\n  allQuizzes {\n    id\n    title\n    description\n    category\n    diff\n    maxPoints\n    timeLimit\n  }\n}": typeof types.AllQuizzesDocument,
    "query Users {\n  allUsers {\n    id\n    username\n    email\n    isActive\n  }\n}": typeof types.UsersDocument,
    "query GetUserLevel($username: String!) {\n  userById(username: $username) {\n    userLevel\n  }\n}": typeof types.GetUserLevelDocument,
    "query QuizById($id: ID!) {\n  quizById(id: $id) {\n    id\n    title\n    description\n    category\n    diff\n    image\n    author {\n      id\n      username\n    }\n    createdAt\n    timeLimit\n    maxPoints\n  }\n}": typeof types.QuizByIdDocument,
};
const documents: Documents = {
    "query AllQuizzes {\n  allQuizzes {\n    id\n    title\n    description\n    category\n    diff\n    maxPoints\n    timeLimit\n  }\n}": types.AllQuizzesDocument,
    "query Users {\n  allUsers {\n    id\n    username\n    email\n    isActive\n  }\n}": types.UsersDocument,
    "query GetUserLevel($username: String!) {\n  userById(username: $username) {\n    userLevel\n  }\n}": types.GetUserLevelDocument,
    "query QuizById($id: ID!) {\n  quizById(id: $id) {\n    id\n    title\n    description\n    category\n    diff\n    image\n    author {\n      id\n      username\n    }\n    createdAt\n    timeLimit\n    maxPoints\n  }\n}": types.QuizByIdDocument,
};

/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 *
 *
 * @example
 * ```ts
 * const query = graphql(`query GetUser($id: ID!) { user(id: $id) { name } }`);
 * ```
 *
 * The query argument is unknown!
 * Please regenerate the types.
 */
export function graphql(source: string): unknown;

/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(source: "query AllQuizzes {\n  allQuizzes {\n    id\n    title\n    description\n    category\n    diff\n    maxPoints\n    timeLimit\n  }\n}"): (typeof documents)["query AllQuizzes {\n  allQuizzes {\n    id\n    title\n    description\n    category\n    diff\n    maxPoints\n    timeLimit\n  }\n}"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(source: "query Users {\n  allUsers {\n    id\n    username\n    email\n    isActive\n  }\n}"): (typeof documents)["query Users {\n  allUsers {\n    id\n    username\n    email\n    isActive\n  }\n}"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(source: "query GetUserLevel($username: String!) {\n  userById(username: $username) {\n    userLevel\n  }\n}"): (typeof documents)["query GetUserLevel($username: String!) {\n  userById(username: $username) {\n    userLevel\n  }\n}"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(source: "query QuizById($id: ID!) {\n  quizById(id: $id) {\n    id\n    title\n    description\n    category\n    diff\n    image\n    author {\n      id\n      username\n    }\n    createdAt\n    timeLimit\n    maxPoints\n  }\n}"): (typeof documents)["query QuizById($id: ID!) {\n  quizById(id: $id) {\n    id\n    title\n    description\n    category\n    diff\n    image\n    author {\n      id\n      username\n    }\n    createdAt\n    timeLimit\n    maxPoints\n  }\n}"];

export function graphql(source: string) {
  return (documents as any)[source] ?? {};
}

export type DocumentType<TDocumentNode extends DocumentNode<any, any>> = TDocumentNode extends DocumentNode<  infer TType,  any>  ? TType  : never;
