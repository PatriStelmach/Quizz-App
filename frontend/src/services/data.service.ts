export default function authHeader()
{
  const user = JSON.parse(<string>localStorage.getItem('user'));

  if (user)
  try
  {
      if (user?.token)
      {
        return {Authorization: 'Bearer ' + user.token};
      }
    }
    catch (e)
    {
      console.error('Błąd parsowania usera:', e);
    }
  }

