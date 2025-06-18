import type { Category, Diff } from '@/generated/graphql.ts'
export type CreateQuiz =
  {
    title: string;
    description: string
    category: Category;
    diff: Diff;
    timeLimit: number;
  }
