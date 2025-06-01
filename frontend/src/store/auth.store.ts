import { defineStore } from 'pinia';
import { auth } from "./auth.store.ts";
import type { User } from "@/types/user.ts";

export const store = defineStore('auth',
  {
    state: () =>
      ({
        user: null as User | null,
        token: null as string | null,
        error: null as string | null
      })
  }
  });

