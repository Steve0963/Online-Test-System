import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import ExamView from '../views/ExamView.vue'
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
          path: '/home/student/test',
          component: () => import('../components/home/student/TestComponent.vue'),
          name: '考试信息',
        },
        {
          path: '/home/student/class',
          component: () => import('../components/home/student/ClassComponent.vue'),
          name: '我的班级'
        },
        {
          path: '/home/student/user',
          component: () => import('../components/home/student/UserComponent.vue'),
          name: '用户管理'
        },
        {
          path: '/home/student/message',
          component: () => import('../components/home/student/MessageComponent.vue'),
          name: '系统信息'
        },
        {
          path: '/home/student/score',
          component: () => import('../components/home/student/ScoreComponent.vue'),
          name: '我的成绩'
        },

        {
          path: '/home/teacher',
          component: () => import('../components/home/teacher/MainComponent.vue'),
          name: '主页',
        },
        {
          path: '/home/teacher/stulist',
          component: () => import('../components/home/teacher/StuListComponent.vue'),
          name: '学生列表',
        },
        {
          path: '/home/teacher/classlist',
          component: () => import('../components/home/teacher/ClassListComponent.vue'),
          name: '班级列表'
        },
        {
          path: '/home/teacher/exam',
          component: () => import('../components/home/teacher/ExamComponent.vue'),
          name: '考试管理'
        },
        {
          path: '/home/teacher/paperlist',
          component: () => import('../components/home/teacher/PaperListComponent.vue'),
          name: '试卷列表'
        },
    
        {
          path: '/home/teacher/problemlist',
          component: () => import('../components/home/teacher/ProblemListComponent.vue'),
          name: '试题列表'
        },

      ]
    },

    {
      path: '/exam',
      name: 'exam',
      component: ExamView
    },
    
  ]
})

export default router
