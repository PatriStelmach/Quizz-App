import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter
({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes:
    [
    {
      path:'/home',
      name:'home',

      component: () => import('../views/Home.vue')
    },
      {
        path:'/login',
        name:'login',

        component: () => import('../views/LoginView.vue')
      }

  ],
})


export default router
