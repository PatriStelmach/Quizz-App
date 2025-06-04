import AuthService from '../services/auth.service';
import type {UserLogin} from "@/types/user.login.ts";
import { defineStore } from "pinia";
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';



const useAuthStore = defineStore('auth',
  {
    state: () =>
      ({
        user: null as UserLogin | null,
        loggedIn: false,
        error: null as any
      }),
    persist:
      {
        key: 'jwt',
        paths: ['user', loggedIn]
      },
    actions:
      {
        async login(user: UserLogin)
        {
          try
          {
            this.user = await AuthService.login(user)
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
            this.user = null
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
