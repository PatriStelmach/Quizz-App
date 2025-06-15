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
      },
      {
        path:'/top-players',
        name:'top-players',

        component: () => import('../views/TopPlayers.vue')
      },
      {
        path:'/user-profile',
        name:'user-profile',

        component: () => import('../views/UserProfile.vue')
      },
      {
        path:'/create-quiz',
        name:'create-quiz',

        component: () => import('../views/CreateQuiz.vue')
      },

      {
        path:'/register',
        name:'register',

        component: () => import('../views/Register.vue')
      },
      {
        path:'/activate-account/:token',
        name:'activate-account',

        component: () => import('../views/ActivateAccount.vue')
      },
      {
        path: '/room-view/:id',
        name: 'room-view',
        component: () => import('@/views/RoomView.vue')
      }
      
  ],
})


export default router
