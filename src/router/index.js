import { createRouter, createWebHistory } from 'vue-router'
import MainLayout from '../layout/MainLayout.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: () => import('../views/Login.vue')
    },
    {
      path: '/',
      component: MainLayout,
      children: [
        {
          path: '',
          name: 'Home',
          component: () => import('../views/Home.vue')
        },
        {
          path: 'class-management',
          name: 'ClassManagement',
          component: () => import('../views/ClassManagement.vue')
        },
        {
          path: 'student-management',
          name: 'StudentManagement',
          component: () => import('../views/StudentManagement.vue')
        },
        {
          path: 'department-management',
          name: 'DepartmentManagement',
          component: () => import('../views/DepartmentManagement.vue')
        },
        {
          path: 'employee-management',
          name: 'EmployeeManagement',
          component: () => import('../views/EmployeeManagement.vue')
        }
      ]
    }
  ]
})

// 路由导航守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path === '/login') {
    if (token) {
      next('/')
    } else {
      next()
    }
  } else {
    if (token) {
      next()
    } else {
      next('/login')
    }
  }
})

export default router
