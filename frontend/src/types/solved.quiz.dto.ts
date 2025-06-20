import { type Category, Diff } from '@/generated/graphql.ts'

export type SolvedDto =
{
  title: string;
  category: Category;
  diff: Diff
  maxPoints: number;
  score: number
}
