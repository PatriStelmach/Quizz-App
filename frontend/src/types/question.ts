import type {Diff} from "@/types/diff.ts";
import type {QuestionType} from "@/types/question.type.ts";

export interface Question {
  id: number | null; // null for new unsaved questions
  diff: Diff;
  questionType: QuestionType;
  question: string;
  openAnswer: string | null;
  correctAnswer: string;
  answers: string[]; // Array of answer strings
}
