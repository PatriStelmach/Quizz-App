import axios from 'axios';
import type {UserLogin} from "@/types/user.login.ts";

const  API_URL = 'http://localhost:10000/api/auth/'

class AuthService
{
  login(user: UserLogin)
  {
    return axios.post(API_URL + 'login',
      {
      username: user.username,
      password: user.password
    })
      .then(response =>
      {
        if (response.data.accessToken)
        {
          localStorage.setItem('user', JSON.stringify(response.data));
        }
        return response.data;
      })
  }

  logout()
  {
    localStorage.removeItem('user');
  }

  register(user: UserLogin)
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
