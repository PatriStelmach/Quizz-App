// import { ApolloClient, InMemoryCache, createHttpLink } from '@apollo/client/core'
// import { setContext } from '@apollo/client/link/context'
//
// const httpLink = createHttpLink({
//   uri: 'http://localhost:10000/graphql'
// })
//
// const authLink = setContext((_, { headers }) =>
// {
//   const token = localStorage.getItem('token')
//   return {
//     headers:
//       {
//         ...headers,
//         authorization: token ? `Bearer ${token}` : ''
//       }
//   }
// })
//
// export const apolloClient = new ApolloClient({
//   link: authLink.concat(httpLink),
//   cache: new InMemoryCache()
// })
//
//
