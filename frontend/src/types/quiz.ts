import type {Diff} from "@/types/diff.ts";
import type {UserDTO} from "@/types/user.ts";
import type {Question} from "@/types/question.ts";

export interface Quiz {
  id: number | null; // null for new unsaved quizzes
  title: string;
  description: string;
  category: string;
  diff: Diff;
  image: string;
  author: UserDTO | number; // Use ID or minimal user info
  createdAt: string; // ISO string (Instant)
  timeLimit: string; // ISO string (LocalDateTime)
  finishedByUsers: UserDTO[] | number[]; // Use IDs
  maxPoints: number;
  userScore: UserDTO[] | number[]; // Use IDs
  questions?: Question[]; // Optional for nested data
}
