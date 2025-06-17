import axios from 'axios';
import type {UserLogin} from "@/types/user.login.ts";
import type {UserRegister} from "@/types/user.register.ts";

const  API_URL = 'http://localhost:10000/auth/'

class AuthService
{
  async login(user: UserLogin)
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
          console.log(localStorage.getItem('user'));
        }

        console.log(response.data);


        return response;
      })
  }

  async getUserByToken()
{
  const token = localStorage.getItem('jwt')
  if (!token)
  {
    throw new Error('No token found')
  }

  const response = await axios.get('http://localhost:10000/user/get-by-token',
    {
    headers:
      {
      Authorization: `Bearer ${token}`
    }
  })

  return response.data
}


  async logout(): Promise<string>
  {
    localStorage.removeItem('user');
    return 'logged out';
  }

  async register(user: UserRegister)
  {
    return axios.post(API_URL + 'register',
      {
        username: user.username,
        email: user.email,
        password: user.password,
        birthDate: user.birthDate
      })
      .then(response =>
      {
        try
        {
          return response.data;
        }
        catch (e)
        {
          throw e
        }
      })
  }

  async activate(token: string)
  {
    return axios.get(API_URL + 'activate-acc?token=' + token)
      .then(response =>
      {
        try
        {
          return response.data;
        }
        catch (e)
        {
          throw (e)
        }
      })
  }
}

export default new AuthService()
