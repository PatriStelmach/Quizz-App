/* eslint-disable */
import type { TypedDocumentNode as DocumentNode } from '@graphql-typed-document-node/core';
export type Maybe<T> = T | null;
export type InputMaybe<T> = Maybe<T>;
export type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]?: Maybe<T[SubKey]> };
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]: Maybe<T[SubKey]> };
export type MakeEmpty<T extends { [key: string]: unknown }, K extends keyof T> = { [_ in K]?: never };
export type Incremental<T> = T | { [P in keyof T]?: P extends ' $fragmentName' | '__typename' ? T[P] : never };
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: { input: string; output: string; }
  String: { input: string; output: string; }
  Boolean: { input: boolean; output: boolean; }
  Int: { input: number; output: number; }
  Float: { input: number; output: number; }
};

export enum Diff {
  Easy = 'EASY',
  Expert = 'EXPERT',
  Hard = 'HARD',
  Medium = 'MEDIUM'
}

export type PageInfo = {
  __typename?: 'PageInfo';
  endCursor?: Maybe<Scalars['String']['output']>;
  hasNextPage: Scalars['Boolean']['output'];
};

export type Query = {
  __typename?: 'Query';
  allQuestions: Array<Question>;
  allQuizzes: Array<Quiz>;
  allUsers: Array<User>;
  questionById?: Maybe<Question>;
  quizById?: Maybe<Quiz>;
  userById?: Maybe<User>;
};


export type QueryQuestionByIdArgs = {
  id?: InputMaybe<Scalars['ID']['input']>;
};


export type QueryQuizByIdArgs = {
  id?: InputMaybe<Scalars['ID']['input']>;
};


export type QueryUserByIdArgs = {
  id?: InputMaybe<Scalars['ID']['input']>;
};

export type Question = {
  __typename?: 'Question';
  answers?: Maybe<Array<Maybe<Scalars['String']['output']>>>;
  correctAnswer?: Maybe<Scalars['String']['output']>;
  diff?: Maybe<Diff>;
  id: Scalars['ID']['output'];
  openAnswer?: Maybe<Scalars['String']['output']>;
  question: Scalars['String']['output'];
  questionType?: Maybe<QuestionType>;
  quiz: Quiz;
};

export enum QuestionType {
  MultipleChoice = 'MULTIPLE_CHOICE',
  Open = 'OPEN',
  SingleChoice = 'SINGLE_CHOICE'
}

export type Quiz = {
  __typename?: 'Quiz';
  author?: Maybe<User>;
  category?: Maybe<Scalars['String']['output']>;
  createdAt?: Maybe<Scalars['String']['output']>;
  description?: Maybe<Scalars['String']['output']>;
  diff?: Maybe<Diff>;
  id: Scalars['ID']['output'];
  image?: Maybe<Scalars['String']['output']>;
  maxPoints?: Maybe<Scalars['Int']['output']>;
  questions?: Maybe<Array<Maybe<Question>>>;
  timeLimit?: Maybe<Scalars['String']['output']>;
  title: Scalars['String']['output'];
};

export type QuizConnection = {
  __typename?: 'QuizConnection';
  edges?: Maybe<Array<QuizEdge>>;
  pageInfo: PageInfo;
};

export type QuizEdge = {
  __typename?: 'QuizEdge';
  cursor: Scalars['String']['output'];
  node: Quiz;
};

export enum Role {
  Admin = 'ADMIN',
  User = 'USER'
}

export type User = {
  __typename?: 'User';
  accountLocked: Scalars['Boolean']['output'];
  avName?: Maybe<Scalars['String']['output']>;
  avType?: Maybe<Scalars['String']['output']>;
  avatar?: Maybe<Scalars['String']['output']>;
  bio?: Maybe<Scalars['String']['output']>;
  birthDate?: Maybe<Scalars['String']['output']>;
  createdAt?: Maybe<Scalars['String']['output']>;
  email?: Maybe<Scalars['String']['output']>;
  enabled: Scalars['Boolean']['output'];
  finishedAmount?: Maybe<Scalars['Int']['output']>;
  id: Scalars['ID']['output'];
  isActive?: Maybe<Scalars['Boolean']['output']>;
  quizzesCreated?: Maybe<Array<Maybe<Quiz>>>;
  roles: Array<Role>;
  score?: Maybe<Scalars['Int']['output']>;
  streak?: Maybe<Scalars['Int']['output']>;
  updatedAt?: Maybe<Scalars['String']['output']>;
  userLevel?: Maybe<UserLevel>;
  username: Scalars['String']['output'];
};

export type UserConnection = {
  __typename?: 'UserConnection';
  edges?: Maybe<Array<UserEdge>>;
  pageInfo: PageInfo;
};

export type UserEdge = {
  __typename?: 'UserEdge';
  cursor: Scalars['String']['output'];
  node: User;
};

export enum UserLevel {
  Casual = 'CASUAL',
  Master = 'MASTER',
  Newbie = 'NEWBIE',
  Noob = 'NOOB',
  Pro = 'PRO'
}

export type UserQuizScore = {
  __typename?: 'UserQuizScore';
  id: Scalars['ID']['output'];
  quiz: Quiz;
  score: Scalars['Int']['output'];
  user: User;
};

export type UsersQueryVariables = Exact<{ [key: string]: never; }>;


export type UsersQuery = { __typename?: 'Query', allUsers: Array<{ __typename?: 'User', id: string, username: string, email?: string | null, isActive?: boolean | null }> };

export type UserByIdQueryVariables = Exact<{ [key: string]: never; }>;


export type UserByIdQuery = { __typename?: 'Query', userById?: { __typename?: 'User', email?: string | null, username: string } | null };


export const UsersDocument = {"kind":"Document","definitions":[{"kind":"OperationDefinition","operation":"query","name":{"kind":"Name","value":"Users"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"allUsers"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"id"}},{"kind":"Field","name":{"kind":"Name","value":"username"}},{"kind":"Field","name":{"kind":"Name","value":"email"}},{"kind":"Field","name":{"kind":"Name","value":"isActive"}}]}}]}}]} as unknown as DocumentNode<UsersQuery, UsersQueryVariables>;
export const UserByIdDocument = {"kind":"Document","definitions":[{"kind":"OperationDefinition","operation":"query","name":{"kind":"Name","value":"UserById"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"userById"},"arguments":[{"kind":"Argument","name":{"kind":"Name","value":"id"},"value":{"kind":"IntValue","value":"1"}}],"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"email"}},{"kind":"Field","name":{"kind":"Name","value":"username"}}]}}]}}]} as unknown as DocumentNode<UserByIdQuery, UserByIdQueryVariables>;
