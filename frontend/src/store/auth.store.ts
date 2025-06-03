import AuthService from '../services/auth.service';
import type {UserLogin} from "@/types/user.login.ts";
import { defineStore } from "pinia";



const useAuthStore = defineStore('auth',
  {
    state: () =>
      ({
        user: JSON.parse(localStorage.getItem('user') || '{}') as UserLogin | null,
        loggedIn: false,
        error: null as any
      }),
    actions:
      {
        async login(user: UserLogin)
        {
          try
          {
            this.user = await AuthService.login(user)
            localStorage.setItem('user', JSON.stringify(this.user))
            this.loggedIn = true
            this.error = null
            return this.user
          }
            catch(error)
            {
              this.loggedIn = false
              throw this.error
            }
          },
          async register(user: UserLogin)
          {
            try
            {
              await AuthService.register(user)
              this.loggedIn = false
              this.error = null
              return 'registered'
            }
            catch (error)
            {
              this.loggedIn = false
              throw this.error
            }
          },
        async logout()
        {
          try
          {
            AuthService.logout()
            this.loggedIn = false
            this.error = null
            return 'logged out'
          }
          catch(error)
          {
            throw this.error
          }
        }
        }

  })

export default useAuthStore
