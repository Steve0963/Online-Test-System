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
          path: '/test',
          component: () => import('../components/home/test/TestComponent.vue'),
          name: '考试信息',
        },
        {
          path: '/class',
          component: () => import('../components/home/class/ClassComponent.vue'),
          name: '班级'
        },
        {
          path: '/user',
          component: () => import('../components/home/user/UserComponent.vue'),
          name: '用户管理'
        }
      ]
    },
    
  ]
})

export default router
