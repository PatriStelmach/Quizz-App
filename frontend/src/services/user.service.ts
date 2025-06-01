import axios from 'axios';
import authHeader from "@/services/data.service.ts";

const API_URL = 'http://localhost:10000/api/user/'
class UserService
{
  getAllUsers()
  {
    return axios.get(API_URL + 'all-users', {headers: authHeader()});
  }

  getUserById(id: number)
  {
    return axios.get(API_URL + id, {headers: authHeader()});
  }
}

export default new UserService()
