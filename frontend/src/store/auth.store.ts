import AuthService from '../services/auth.service';
import type {UserLogin} from "@/types/user.login.ts";
import { defineStore } from "pinia";
import { ref } from 'vue';
import type { UserRegister } from '@/types/user.register.ts'
import { jwtDecode } from 'jwt-decode'
import type { JwtPayload } from '@/types/JwtPayload.ts'



const authStore = defineStore('user',
  () =>
  {
    const user = ref<UserLogin | null>(null);
    const loggedIn = ref(false);
    const error = ref<string | null>(null);
    const token = ref<string | null>(null);

    const setToken = (newToken: string | null) =>
    {
      token.value = newToken;
    }
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

        const decoded = jwtDecode<JwtPayload>(loggedInUser.data.token);
        setToken(loggedInUser.data.token);
        setUser({ username: decoded.username, password: '' });
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

    const googleLogin = async (token: string) =>
    {
      try
      {
        if (!token)
        {
          throw new Error('Invalid token from Google');
        }
        const decoded = jwtDecode<JwtPayload>(token);
        const userObject =
          {
            user: { username: decoded.username, password: '' },
            loggedIn: true,
            error: null,
            token
          };
        localStorage.setItem('user', JSON.stringify(userObject));
        setUser({ username: decoded.username, password: '' });
        setToken(userObject.token);
        setLoggedIn(true);
        setError(null);

        return { token };
      }
      catch (err)
      {
        setLoggedIn(false);
        setError(err instanceof Error ? err.message : 'An unknown error occurred');
        throw err;
      }
    }

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
      googleLogin,
      activate,
      login,
      register,
      logout,
      setUser,
      setLoggedIn,
      setError,
      clearUser,
      token,
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
