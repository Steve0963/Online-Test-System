import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: LoginView
    },
    {
      path: '/home',
      name: 'home',
      component: () => import('../views/HomeView.vue'),
      children: [
        {
          path: '/home/test',
          component: () => import('../components/home/TestComponent.vue'),
          name: '考试信息',
        },
        {
          path: '/home/class',
          component: () => import('../components/home/ClassComponent.vue'),
          name: '我的班级'
        },
        {
          path: '/home/user',
          component: () => import('../components/home/UserComponent.vue'),
          name: '用户管理'
        },
        {
          path: '/home/message',
          component: () => import('../components/home/MessageComponent.vue'),
          name: '系统信息'
        },
        {
          path: '/home/score',
          component: () => import('../components/home/ScoreComponent.vue'),
          name: '我的成绩'
        },
      ]
    },
    
  ]
})

export default router
