import axios from 'axios';
import type {UserLogin} from "@/types/user.login.ts";
import type {UserRegister} from "@/types/user.register.ts";

const  API_URL = 'http://localhost:10000/auth/'

class AuthService
{
  login(user: UserLogin)
  {
    return axios.post(API_URL + 'login',
      {
      identifier: user.username,
      password: user.password
    })
      .then(response =>
      {
        if (response.data.token)
        {
          localStorage.setItem('user', JSON.stringify(response.data));
          console.log(localStorage.getItem('user'));
        }

        return response.data;
      })
  }


  logout(): void
  {
    localStorage.removeItem('user');
  }

  register(user: UserRegister)
  {
    return axios.post(API_URL + 'register',
      {
        username: user.username,
        email: user.email,
        password: user.password
      })
  }
}

export default new AuthService()
