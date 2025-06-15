import AuthService from '../services/auth.service';
import type {UserLogin} from "@/types/user.login.ts";
import { defineStore } from "pinia";
import { ref } from 'vue';
import type { UserRegister } from '@/types/user.register.ts'



const authStore = defineStore('user',
   () =>
      {
        const user = ref<UserLogin | null>(null);
        const loggedIn = ref(false);
        const error = ref<string | null>(null);

        const setUser = (newUser: UserLogin) =>
        {
          user.value = newUser;
        };


        const setLoggedIn = (status: boolean) =>
        {
          loggedIn.value = status;
        };

        const setError = (newError: string | null) =>
        {
          error.value = newError;
        };

        const clearUser = () =>
        {
          user.value = null;
          loggedIn.value = false;
          error.value = null;
        };

        const login = async (user: UserLogin) =>
        {
          try
          {
            const loggedInUser = await AuthService.login(user);
            setUser(loggedInUser);
            setLoggedIn(true);
            setError(null);
            return loggedInUser;
          }
          catch (err)
          {
            setLoggedIn(false);
            setError(err instanceof Error ? err.message : 'An unknown error occurred');
            throw err;
          }
        };

        const activate = async (token: string) =>
        {
          try
          {
            const loggenInUser = await AuthService.activate(token);
            setUser(loggenInUser);
            setLoggedIn(true);
            setError(null);
            return loggenInUser;
          }
          catch (err)
          {
            setLoggedIn(false);
            setError(err instanceof Error ? err.message : 'An unknown error occurred');
            throw err;
          }
        }

        const register = async (user: UserRegister) =>
        {
          try
          {
            await AuthService.register(user);
            setLoggedIn(false);
            setError(null);
            return 'registered';
          }
          catch (err)
          {
            setLoggedIn(false);
            setError(err instanceof Error ? err.message : 'An unknown error occurred');
            throw err;
          }
        };

        const logout = async () =>
        {
          try
          {
            await AuthService.logout();
            clearUser();
            return 'logged out';
          }
          catch (err)
          {
            setError(err instanceof Error ? err.message : 'An unknown error occurred');
            throw err;
          }
        };

        return{
          user,
          loggedIn,
          error,
          activate,
          login,
          register,
          logout,
          setUser,
          setLoggedIn,
          setError,
          clearUser,
        }
        },
  {
    persist:
      {
          key: 'user',
          storage: localStorage,
    }
  });


export default authStore
