import type {Diff} from "@/types/diff.ts";
import type {User} from "@/types/user.ts";
import type {Question} from "@/types/question.ts";

export interface Quiz {
  id: number | null; // null for new unsaved quizzes
  title: string;
  description: string;
  category: string;
  diff: Diff;
  image: string;
  author: User | number; // Use ID or minimal user info
  createdAt: string; // ISO string (Instant)
  timeLimit: string; // ISO string (LocalDateTime)
  finishedByUsers: User[] | number[]; // Use IDs
  maxPoints: number;
  userScore: User[] | number[]; // Use IDs
  questions?: Question[]; // Optional for nested data
}
