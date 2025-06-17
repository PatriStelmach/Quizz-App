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
  /** Binary data */
  ByteArray: { input: any; output: any; }
  /** A duration scalar that handles java.time.Duration in ISO-8601 format */
  Duration: { input: any; output: any; }
};

export type Answer = {
  __typename?: 'Answer';
  answer?: Maybe<Scalars['String']['output']>;
  correct?: Maybe<Scalars['Boolean']['output']>;
  id: Scalars['ID']['output'];
  question?: Maybe<Question>;
};

export enum Category {
  Art = 'ART',
  Geography = 'GEOGRAPHY',
  History = 'HISTORY',
  Literature = 'LITERATURE',
  Mathematics = 'MATHEMATICS',
  Movies = 'MOVIES',
  Music = 'MUSIC',
  Science = 'SCIENCE',
  Sports = 'SPORTS',
  Technology = 'TECHNOLOGY'
}

export enum Diff {
  Easy = 'EASY',
  Expert = 'EXPERT',
  Hard = 'HARD',
  Medium = 'MEDIUM'
}

export type Mutation = {
  __typename?: 'Mutation';
  updateQuiz: Quiz;
  updateUser: User;
};


export type MutationUpdateQuizArgs = {
  id: Scalars['ID']['input'];
  input?: InputMaybe<QuizInput>;
};


export type MutationUpdateUserArgs = {
  input: UserInput;
  username: Scalars['String']['input'];
};

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
  quiz?: Maybe<Quiz>;
};

export type QuestionInput = {
  answers?: InputMaybe<Array<InputMaybe<Scalars['String']['input']>>>;
  correctAnswer?: InputMaybe<Scalars['String']['input']>;
  diff?: InputMaybe<Diff>;
  id: Scalars['ID']['input'];
  openAnswer?: InputMaybe<Scalars['String']['input']>;
  question: Scalars['String']['input'];
  questionType?: InputMaybe<QuestionType>;
  quiz: QuizInput;
};

export enum QuestionType {
  MultipleChoice = 'MULTIPLE_CHOICE',
  Open = 'OPEN',
  SingleChoice = 'SINGLE_CHOICE'
}

export type Quiz = {
  __typename?: 'Quiz';
  author?: Maybe<User>;
  category?: Maybe<Category>;
  createdAt?: Maybe<Scalars['String']['output']>;
  description?: Maybe<Scalars['String']['output']>;
  diff?: Maybe<Diff>;
  id: Scalars['ID']['output'];
  image?: Maybe<Scalars['ByteArray']['output']>;
  maxPoints?: Maybe<Scalars['Int']['output']>;
  questions?: Maybe<Array<Maybe<Question>>>;
  timeLimit?: Maybe<Scalars['Duration']['output']>;
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

export type QuizInput = {
  author?: InputMaybe<UserInput>;
  category?: InputMaybe<Category>;
  createdAt?: InputMaybe<Scalars['String']['input']>;
  description?: InputMaybe<Scalars['String']['input']>;
  diff?: InputMaybe<Diff>;
  id: Scalars['ID']['input'];
  image?: InputMaybe<Scalars['ByteArray']['input']>;
  maxPoints?: InputMaybe<Scalars['Int']['input']>;
  questions?: InputMaybe<Array<InputMaybe<QuestionInput>>>;
  timeLimit?: InputMaybe<Scalars['Duration']['input']>;
  title: Scalars['String']['input'];
};

export enum Role {
  Admin = 'ADMIN',
  User = 'USER'
}

export type User = {
  __typename?: 'User';
  avName?: Maybe<Scalars['String']['output']>;
  avType?: Maybe<Scalars['String']['output']>;
  avatar?: Maybe<Scalars['ByteArray']['output']>;
  bio?: Maybe<Scalars['String']['output']>;
  createdAt?: Maybe<Scalars['String']['output']>;
  email?: Maybe<Scalars['String']['output']>;
  enabled?: Maybe<Scalars['Boolean']['output']>;
  finishedAmount?: Maybe<Scalars['Int']['output']>;
  id: Scalars['ID']['output'];
  isActive?: Maybe<Scalars['Boolean']['output']>;
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

export type UserInput = {
  avName?: InputMaybe<Scalars['String']['input']>;
  avType?: InputMaybe<Scalars['String']['input']>;
  avatar?: InputMaybe<Scalars['ByteArray']['input']>;
  bio?: InputMaybe<Scalars['String']['input']>;
  createdAt?: InputMaybe<Scalars['String']['input']>;
  email?: InputMaybe<Scalars['String']['input']>;
  enabled?: InputMaybe<Scalars['Boolean']['input']>;
  finishedAmount?: InputMaybe<Scalars['Int']['input']>;
  id?: InputMaybe<Scalars['ID']['input']>;
  isActive?: InputMaybe<Scalars['Boolean']['input']>;
  quizzesCreated?: InputMaybe<Array<InputMaybe<QuizInput>>>;
  score?: InputMaybe<Scalars['Int']['input']>;
  streak?: InputMaybe<Scalars['Int']['input']>;
  updatedAt?: InputMaybe<Scalars['String']['input']>;
  userLevel?: InputMaybe<UserLevel>;
  username: Scalars['String']['input'];
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

export type AllQuizzesQueryVariables = Exact<{ [key: string]: never; }>;


export type AllQuizzesQuery = { __typename?: 'Query', allQuizzes: Array<{ __typename?: 'Quiz', id: string, title: string, description?: string | null, category?: Category | null, diff?: Diff | null }> };

export type UsersQueryVariables = Exact<{ [key: string]: never; }>;


export type UsersQuery = { __typename?: 'Query', allUsers: Array<{ __typename?: 'User', id: string, username: string, email?: string | null, isActive?: boolean | null }> };

export type QuizByIdQueryVariables = Exact<{
  id: Scalars['ID']['input'];
}>;


export type QuizByIdQuery = { __typename?: 'Query', quizById?: { __typename?: 'Quiz', id: string, title: string, description?: string | null, category?: Category | null, diff?: Diff | null, image?: any | null, createdAt?: string | null, timeLimit?: any | null, maxPoints?: number | null, author?: { __typename?: 'User', id: string, username: string } | null } | null };

export type UserByIdQueryVariables = Exact<{ [key: string]: never; }>;


export type UserByIdQuery = { __typename?: 'Query', userById?: { __typename?: 'User', email?: string | null, username: string } | null };


export const AllQuizzesDocument = {"kind":"Document","definitions":[{"kind":"OperationDefinition","operation":"query","name":{"kind":"Name","value":"AllQuizzes"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"allQuizzes"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"id"}},{"kind":"Field","name":{"kind":"Name","value":"title"}},{"kind":"Field","name":{"kind":"Name","value":"description"}},{"kind":"Field","name":{"kind":"Name","value":"category"}},{"kind":"Field","name":{"kind":"Name","value":"diff"}}]}}]}}]} as unknown as DocumentNode<AllQuizzesQuery, AllQuizzesQueryVariables>;
export const UsersDocument = {"kind":"Document","definitions":[{"kind":"OperationDefinition","operation":"query","name":{"kind":"Name","value":"Users"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"allUsers"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"id"}},{"kind":"Field","name":{"kind":"Name","value":"username"}},{"kind":"Field","name":{"kind":"Name","value":"email"}},{"kind":"Field","name":{"kind":"Name","value":"isActive"}}]}}]}}]} as unknown as DocumentNode<UsersQuery, UsersQueryVariables>;
export const QuizByIdDocument = {"kind":"Document","definitions":[{"kind":"OperationDefinition","operation":"query","name":{"kind":"Name","value":"QuizById"},"variableDefinitions":[{"kind":"VariableDefinition","variable":{"kind":"Variable","name":{"kind":"Name","value":"id"}},"type":{"kind":"NonNullType","type":{"kind":"NamedType","name":{"kind":"Name","value":"ID"}}}}],"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"quizById"},"arguments":[{"kind":"Argument","name":{"kind":"Name","value":"id"},"value":{"kind":"Variable","name":{"kind":"Name","value":"id"}}}],"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"id"}},{"kind":"Field","name":{"kind":"Name","value":"title"}},{"kind":"Field","name":{"kind":"Name","value":"description"}},{"kind":"Field","name":{"kind":"Name","value":"category"}},{"kind":"Field","name":{"kind":"Name","value":"diff"}},{"kind":"Field","name":{"kind":"Name","value":"image"}},{"kind":"Field","name":{"kind":"Name","value":"author"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"id"}},{"kind":"Field","name":{"kind":"Name","value":"username"}}]}},{"kind":"Field","name":{"kind":"Name","value":"createdAt"}},{"kind":"Field","name":{"kind":"Name","value":"timeLimit"}},{"kind":"Field","name":{"kind":"Name","value":"maxPoints"}}]}}]}}]} as unknown as DocumentNode<QuizByIdQuery, QuizByIdQueryVariables>;
export const UserByIdDocument = {"kind":"Document","definitions":[{"kind":"OperationDefinition","operation":"query","name":{"kind":"Name","value":"UserById"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"userById"},"arguments":[{"kind":"Argument","name":{"kind":"Name","value":"id"},"value":{"kind":"IntValue","value":"1"}}],"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"email"}},{"kind":"Field","name":{"kind":"Name","value":"username"}}]}}]}}]} as unknown as DocumentNode<UserByIdQuery, UserByIdQueryVariables>;
