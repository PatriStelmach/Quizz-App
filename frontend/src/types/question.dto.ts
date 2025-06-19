import type { QuestionType,  } from '@/generated/graphql.ts'
import type { AnswerDto } from '@/types/answer.dto.ts'



export type QuestionDto =
  {
    id: number;
  answers: AnswerDto[];
  questionText: string
  questionType: QuestionType;
  quizId : number;
};
