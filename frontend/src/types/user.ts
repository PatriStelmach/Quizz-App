import type {Role} from "@/types/role.ts";
import type {UserLevel} from "@/types/userLevel.ts";
import type {Quiz} from "@/types/quiz.ts";

export interface UserDTO {
  username: string;
  roles: Role[];
  email: string;
  avatar: string; // Base64-encoded string
  bio: string | null;
  isActive: boolean;
  accountLocked: boolean;
  enabled: boolean;
  createdAt: string; // ISO string (Instant)
  updatedAt: string; // ISO string (LocalDateTime)
  birthDate: string; // YYYY-MM-DD (LocalDate)
  userLevel: UserLevel;
  streak: number | null;
  score: number | null;
  finished_amount: number | null;
  finished_quizzes: Quiz[] | number[]; // Use IDs to avoid circular issues
  quizScore: Quiz[] | number[]; // Use IDs to avoid circular issues
  friendsList: UserDTO[] | number[]; // Use IDs to avoid circular issues
  quizzes: Quiz[] | number[]; // Use IDs to avoid circular issues
}
