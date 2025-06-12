import './assets/globals.css'

import { createApp, h, provide } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import { DefaultApolloClient } from '@vue/apollo-composable'
import { ApolloClient, createHttpLink, InMemoryCache } from '@apollo/client/core'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

const httpLink = createHttpLink({
  // You should use an absolute URL here
  uri: 'http://localhost:10000/graphql',
})

const cache = new InMemoryCache()

const apolloClient = new ApolloClient({
  link: httpLink,
  cache,
})

const app = createApp({
  setup()
  {
    provide(DefaultApolloClient, apolloClient)
  },

  render: () => h(App),
})

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)
app.use(pinia)
app.use(router)

app.mount('#app')

export default apolloClient
