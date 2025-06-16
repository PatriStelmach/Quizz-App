import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter
({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes:
    [
    {
      path:'/home',
      name:'home',

      component: () => import('../views/HomeView.vue')
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
        path:'/activate-account',
        name:'activate-account',

        component: () => import('../views/ActivateAccount.vue')
      },
      {
        path:'/dashboard',
        name:'dashboard',

        component: () => import('../views/DashboardView.vue')
      },
      {
        path: '/room-view/:id',
        name: 'room-view',
        component: () => import('../views/RoomView.vue')
      },
      {
        path: '/quiz-game',
        name: 'quiz-game',
        component: () => import('../views/QuizGame.vue')
      },
      {
        path: '/room/:roomId',
        name: 'room',
        component: () => import('@/views/RoomView.vue'),
        props: true
      },
      {
        path: '/game/:roomId',
        name: 'game',
        component: () => import('@/views/GameView.vue'),
        props: true
      },

  ],
})


export default router
