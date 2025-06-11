import { GraphQLResolveInfo } from 'graphql';
import { DataSourceContext } from '../types/DataSourceContext';
import { gql } from '@apollo/client';
export type Maybe<T> = T | null;
export type InputMaybe<T> = Maybe<T>;
export type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]?: Maybe<T[SubKey]> };
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]: Maybe<T[SubKey]> };
export type MakeEmpty<T extends { [key: string]: unknown }, K extends keyof T> = { [_ in K]?: never };
export type Incremental<T> = T | { [P in keyof T]?: P extends ' $fragmentName' | '__typename' ? T[P] : never };
export type RequireFields<T, K extends keyof T> = Omit<T, K> & { [P in K]-?: NonNullable<T[P]> };
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: { input: string; output: string; }
  String: { input: string; output: string; }
  Boolean: { input: boolean; output: boolean; }
  Int: { input: number; output: number; }
  Float: { input: number; output: number; }
  _FieldSet: { input: any; output: any; }
};

export enum Diff {
  Easy = 'EASY',
  Expert = 'EXPERT',
  Hard = 'HARD',
  Medium = 'MEDIUM'
}

export type Mutation = {
  __typename?: 'Mutation';
  updateUser: User;
};


export type MutationUpdateUserArgs = {
  id: Scalars['ID']['input'];
  input: UserInput;
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
  quiz: Quiz;
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

export type QuizInput = {
  author?: InputMaybe<UserInput>;
  category?: InputMaybe<Scalars['String']['input']>;
  createdAt?: InputMaybe<Scalars['String']['input']>;
  description?: InputMaybe<Scalars['String']['input']>;
  diff?: InputMaybe<Diff>;
  id: Scalars['ID']['input'];
  image?: InputMaybe<Scalars['String']['input']>;
  maxPoints?: InputMaybe<Scalars['Int']['input']>;
  questions?: InputMaybe<Array<InputMaybe<QuestionInput>>>;
  timeLimit?: InputMaybe<Scalars['String']['input']>;
  title: Scalars['String']['input'];
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

export type UserInput = {
  accountLocked: Scalars['Boolean']['input'];
  avName?: InputMaybe<Scalars['String']['input']>;
  avType?: InputMaybe<Scalars['String']['input']>;
  avatar?: InputMaybe<Scalars['String']['input']>;
  bio?: InputMaybe<Scalars['String']['input']>;
  birthDate?: InputMaybe<Scalars['String']['input']>;
  createdAt?: InputMaybe<Scalars['String']['input']>;
  email?: InputMaybe<Scalars['String']['input']>;
  enabled: Scalars['Boolean']['input'];
  finishedAmount?: InputMaybe<Scalars['Int']['input']>;
  id: Scalars['ID']['input'];
  isActive?: InputMaybe<Scalars['Boolean']['input']>;
  quizzesCreated?: InputMaybe<Array<InputMaybe<QuizInput>>>;
  roles: Array<Role>;
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

export type WithIndex<TObject> = TObject & Record<string, any>;
export type ResolversObject<TObject> = WithIndex<TObject>;

export type ResolverTypeWrapper<T> = Promise<T> | T;


export type ResolverWithResolve<TResult, TParent, TContext, TArgs> = {
  resolve: ResolverFn<TResult, TParent, TContext, TArgs>;
};
export type Resolver<TResult, TParent = {}, TContext = {}, TArgs = {}> = ResolverFn<TResult, TParent, TContext, TArgs> | ResolverWithResolve<TResult, TParent, TContext, TArgs>;

export type ResolverFn<TResult, TParent, TContext, TArgs> = (
  parent: TParent,
  args: TArgs,
  context: TContext,
  info: GraphQLResolveInfo
) => Promise<TResult> | TResult;

export type SubscriptionSubscribeFn<TResult, TParent, TContext, TArgs> = (
  parent: TParent,
  args: TArgs,
  context: TContext,
  info: GraphQLResolveInfo
) => AsyncIterable<TResult> | Promise<AsyncIterable<TResult>>;

export type SubscriptionResolveFn<TResult, TParent, TContext, TArgs> = (
  parent: TParent,
  args: TArgs,
  context: TContext,
  info: GraphQLResolveInfo
) => TResult | Promise<TResult>;

export interface SubscriptionSubscriberObject<TResult, TKey extends string, TParent, TContext, TArgs> {
  subscribe: SubscriptionSubscribeFn<{ [key in TKey]: TResult }, TParent, TContext, TArgs>;
  resolve?: SubscriptionResolveFn<TResult, { [key in TKey]: TResult }, TContext, TArgs>;
}

export interface SubscriptionResolverObject<TResult, TParent, TContext, TArgs> {
  subscribe: SubscriptionSubscribeFn<any, TParent, TContext, TArgs>;
  resolve: SubscriptionResolveFn<TResult, any, TContext, TArgs>;
}

export type SubscriptionObject<TResult, TKey extends string, TParent, TContext, TArgs> =
  | SubscriptionSubscriberObject<TResult, TKey, TParent, TContext, TArgs>
  | SubscriptionResolverObject<TResult, TParent, TContext, TArgs>;

export type SubscriptionResolver<TResult, TKey extends string, TParent = {}, TContext = {}, TArgs = {}> =
  | ((...args: any[]) => SubscriptionObject<TResult, TKey, TParent, TContext, TArgs>)
  | SubscriptionObject<TResult, TKey, TParent, TContext, TArgs>;

export type TypeResolveFn<TTypes, TParent = {}, TContext = {}> = (
  parent: TParent,
  context: TContext,
  info: GraphQLResolveInfo
) => Maybe<TTypes> | Promise<Maybe<TTypes>>;

export type IsTypeOfResolverFn<T = {}, TContext = {}> = (obj: T, context: TContext, info: GraphQLResolveInfo) => boolean | Promise<boolean>;

export type NextResolverFn<T> = () => Promise<T>;

export type DirectiveResolverFn<TResult = {}, TParent = {}, TContext = {}, TArgs = {}> = (
  next: NextResolverFn<TResult>,
  parent: TParent,
  args: TArgs,
  context: TContext,
  info: GraphQLResolveInfo
) => TResult | Promise<TResult>;



/** Mapping between all available schema types and the resolvers types */
export type ResolversTypes = ResolversObject<{
  Diff: Diff;
  Mutation: ResolverTypeWrapper<{}>;
  ID: ResolverTypeWrapper<Scalars['ID']['output']>;
  PageInfo: ResolverTypeWrapper<PageInfo>;
  String: ResolverTypeWrapper<Scalars['String']['output']>;
  Boolean: ResolverTypeWrapper<Scalars['Boolean']['output']>;
  Query: ResolverTypeWrapper<{}>;
  Question: ResolverTypeWrapper<Question>;
  QuestionInput: QuestionInput;
  QuestionType: QuestionType;
  Quiz: ResolverTypeWrapper<Quiz>;
  Int: ResolverTypeWrapper<Scalars['Int']['output']>;
  QuizConnection: ResolverTypeWrapper<QuizConnection>;
  QuizEdge: ResolverTypeWrapper<QuizEdge>;
  QuizInput: QuizInput;
  Role: Role;
  User: ResolverTypeWrapper<User>;
  UserConnection: ResolverTypeWrapper<UserConnection>;
  UserEdge: ResolverTypeWrapper<UserEdge>;
  UserInput: UserInput;
  UserLevel: UserLevel;
  UserQuizScore: ResolverTypeWrapper<UserQuizScore>;
}>;

/** Mapping between all available schema types and the resolvers parents */
export type ResolversParentTypes = ResolversObject<{
  Mutation: {};
  ID: Scalars['ID']['output'];
  PageInfo: PageInfo;
  String: Scalars['String']['output'];
  Boolean: Scalars['Boolean']['output'];
  Query: {};
  Question: Question;
  QuestionInput: QuestionInput;
  Quiz: Quiz;
  Int: Scalars['Int']['output'];
  QuizConnection: QuizConnection;
  QuizEdge: QuizEdge;
  QuizInput: QuizInput;
  User: User;
  UserConnection: UserConnection;
  UserEdge: UserEdge;
  UserInput: UserInput;
  UserQuizScore: UserQuizScore;
}>;

export type DeferDirectiveArgs = {
  if: Scalars['Boolean']['input'];
  label?: Maybe<Scalars['String']['input']>;
};

export type DeferDirectiveResolver<Result, Parent, ContextType = DataSourceContext, Args = DeferDirectiveArgs> = DirectiveResolverFn<Result, Parent, ContextType, Args>;

export type Experimental_DisableErrorPropagationDirectiveArgs = { };

export type Experimental_DisableErrorPropagationDirectiveResolver<Result, Parent, ContextType = DataSourceContext, Args = Experimental_DisableErrorPropagationDirectiveArgs> = DirectiveResolverFn<Result, Parent, ContextType, Args>;

export type MutationResolvers<ContextType = DataSourceContext, ParentType extends ResolversParentTypes['Mutation'] = ResolversParentTypes['Mutation']> = ResolversObject<{
  updateUser?: Resolver<ResolversTypes['User'], ParentType, ContextType, RequireFields<MutationUpdateUserArgs, 'id' | 'input'>>;
}>;

export type PageInfoResolvers<ContextType = DataSourceContext, ParentType extends ResolversParentTypes['PageInfo'] = ResolversParentTypes['PageInfo']> = ResolversObject<{
  endCursor?: Resolver<Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  hasNextPage?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
}>;

export type QueryResolvers<ContextType = DataSourceContext, ParentType extends ResolversParentTypes['Query'] = ResolversParentTypes['Query']> = ResolversObject<{
  allQuestions?: Resolver<Array<ResolversTypes['Question']>, ParentType, ContextType>;
  allQuizzes?: Resolver<Array<ResolversTypes['Quiz']>, ParentType, ContextType>;
  allUsers?: Resolver<Array<ResolversTypes['User']>, ParentType, ContextType>;
  questionById?: Resolver<Maybe<ResolversTypes['Question']>, ParentType, ContextType, Partial<QueryQuestionByIdArgs>>;
  quizById?: Resolver<Maybe<ResolversTypes['Quiz']>, ParentType, ContextType, Partial<QueryQuizByIdArgs>>;
  userById?: Resolver<Maybe<ResolversTypes['User']>, ParentType, ContextType, Partial<QueryUserByIdArgs>>;
}>;

export type QuestionResolvers<ContextType = DataSourceContext, ParentType extends ResolversParentTypes['Question'] = ResolversParentTypes['Question']> = ResolversObject<{
  answers?: Resolver<Maybe<Array<Maybe<ResolversTypes['String']>>>, ParentType, ContextType>;
  correctAnswer?: Resolver<Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  diff?: Resolver<Maybe<ResolversTypes['Diff']>, ParentType, ContextType>;
  id?: Resolver<ResolversTypes['ID'], ParentType, ContextType>;
  openAnswer?: Resolver<Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  question?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  questionType?: Resolver<Maybe<ResolversTypes['QuestionType']>, ParentType, ContextType>;
  quiz?: Resolver<ResolversTypes['Quiz'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
}>;

export type QuizResolvers<ContextType = DataSourceContext, ParentType extends ResolversParentTypes['Quiz'] = ResolversParentTypes['Quiz']> = ResolversObject<{
  author?: Resolver<Maybe<ResolversTypes['User']>, ParentType, ContextType>;
  category?: Resolver<Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  createdAt?: Resolver<Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  description?: Resolver<Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  diff?: Resolver<Maybe<ResolversTypes['Diff']>, ParentType, ContextType>;
  id?: Resolver<ResolversTypes['ID'], ParentType, ContextType>;
  image?: Resolver<Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  maxPoints?: Resolver<Maybe<ResolversTypes['Int']>, ParentType, ContextType>;
  questions?: Resolver<Maybe<Array<Maybe<ResolversTypes['Question']>>>, ParentType, ContextType>;
  timeLimit?: Resolver<Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  title?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
}>;

export type QuizConnectionResolvers<ContextType = DataSourceContext, ParentType extends ResolversParentTypes['QuizConnection'] = ResolversParentTypes['QuizConnection']> = ResolversObject<{
  edges?: Resolver<Maybe<Array<ResolversTypes['QuizEdge']>>, ParentType, ContextType>;
  pageInfo?: Resolver<ResolversTypes['PageInfo'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
}>;

export type QuizEdgeResolvers<ContextType = DataSourceContext, ParentType extends ResolversParentTypes['QuizEdge'] = ResolversParentTypes['QuizEdge']> = ResolversObject<{
  cursor?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  node?: Resolver<ResolversTypes['Quiz'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
}>;

export type UserResolvers<ContextType = DataSourceContext, ParentType extends ResolversParentTypes['User'] = ResolversParentTypes['User']> = ResolversObject<{
  accountLocked?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  avName?: Resolver<Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  avType?: Resolver<Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  avatar?: Resolver<Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  bio?: Resolver<Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  birthDate?: Resolver<Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  createdAt?: Resolver<Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  email?: Resolver<Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  enabled?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  finishedAmount?: Resolver<Maybe<ResolversTypes['Int']>, ParentType, ContextType>;
  id?: Resolver<ResolversTypes['ID'], ParentType, ContextType>;
  isActive?: Resolver<Maybe<ResolversTypes['Boolean']>, ParentType, ContextType>;
  quizzesCreated?: Resolver<Maybe<Array<Maybe<ResolversTypes['Quiz']>>>, ParentType, ContextType>;
  roles?: Resolver<Array<ResolversTypes['Role']>, ParentType, ContextType>;
  score?: Resolver<Maybe<ResolversTypes['Int']>, ParentType, ContextType>;
  streak?: Resolver<Maybe<ResolversTypes['Int']>, ParentType, ContextType>;
  updatedAt?: Resolver<Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  userLevel?: Resolver<Maybe<ResolversTypes['UserLevel']>, ParentType, ContextType>;
  username?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
}>;

export type UserConnectionResolvers<ContextType = DataSourceContext, ParentType extends ResolversParentTypes['UserConnection'] = ResolversParentTypes['UserConnection']> = ResolversObject<{
  edges?: Resolver<Maybe<Array<ResolversTypes['UserEdge']>>, ParentType, ContextType>;
  pageInfo?: Resolver<ResolversTypes['PageInfo'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
}>;

export type UserEdgeResolvers<ContextType = DataSourceContext, ParentType extends ResolversParentTypes['UserEdge'] = ResolversParentTypes['UserEdge']> = ResolversObject<{
  cursor?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  node?: Resolver<ResolversTypes['User'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
}>;

export type UserQuizScoreResolvers<ContextType = DataSourceContext, ParentType extends ResolversParentTypes['UserQuizScore'] = ResolversParentTypes['UserQuizScore']> = ResolversObject<{
  id?: Resolver<ResolversTypes['ID'], ParentType, ContextType>;
  quiz?: Resolver<ResolversTypes['Quiz'], ParentType, ContextType>;
  score?: Resolver<ResolversTypes['Int'], ParentType, ContextType>;
  user?: Resolver<ResolversTypes['User'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
}>;

export type Resolvers<ContextType = DataSourceContext> = ResolversObject<{
  Mutation?: MutationResolvers<ContextType>;
  PageInfo?: PageInfoResolvers<ContextType>;
  Query?: QueryResolvers<ContextType>;
  Question?: QuestionResolvers<ContextType>;
  Quiz?: QuizResolvers<ContextType>;
  QuizConnection?: QuizConnectionResolvers<ContextType>;
  QuizEdge?: QuizEdgeResolvers<ContextType>;
  User?: UserResolvers<ContextType>;
  UserConnection?: UserConnectionResolvers<ContextType>;
  UserEdge?: UserEdgeResolvers<ContextType>;
  UserQuizScore?: UserQuizScoreResolvers<ContextType>;
}>;

export type DirectiveResolvers<ContextType = DataSourceContext> = ResolversObject<{
  defer?: DeferDirectiveResolver<any, any, ContextType>;
  experimental_disableErrorPropagation?: Experimental_DisableErrorPropagationDirectiveResolver<any, any, ContextType>;
}>;
