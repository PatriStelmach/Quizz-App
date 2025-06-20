import type { Diff } from '@/generated/graphql.ts'

export type DiffAverage =
  {
  diff: Diff;
  averagePercentage: number;
}
