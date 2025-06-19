
import type { AnswerDto } from '@/types/answer.dto.ts'



export type QuestionDto =
  {
    id: number;
  answers: AnswerDto[];
  questionText: string
  quizId : number;
};
