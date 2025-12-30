import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: () => import('@/views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import('@/views/Dashboard.vue'),
        meta: { title: '首页', icon: 'HomeFilled' }
      },
      {
        path: '/data-init',
        name: 'DataInit',
        component: () => import('@/views/DataInit.vue'),
        meta: { title: '数据初始化', icon: 'Setting' }
      },
      {
        path: '/course-selection',
        name: 'CourseSelection',
        component: () => import('@/views/CourseSelection.vue'),
        meta: { title: '选课管理', icon: 'Document' }
      },
      {
        path: '/grade-management',
        name: 'GradeManagement',
        component: () => import('@/views/GradeManagement.vue'),
        meta: { title: '成绩管理', icon: 'Edit' }
      },
      {
        path: '/query-statistics',
        name: 'QueryStatistics',
        component: () => import('@/views/QueryStatistics.vue'),
        meta: { title: '查询统计', icon: 'DataAnalysis' }
      },
      {
        path: '/students',
        name: 'Students',
        component: () => import('@/views/Students.vue'),
        meta: { title: '学生管理', icon: 'User' }
      },
      {
        path: '/teachers',
        name: 'Teachers',
        component: () => import('@/views/Teachers.vue'),
        meta: { title: '教师管理', icon: 'UserFilled' }
      },
      {
        path: '/courses',
        name: 'Courses',
        component: () => import('@/views/Courses.vue'),
        meta: { title: '课程管理', icon: 'Reading' }
      },
      {
        path: '/teaching-classes',
        name: 'TeachingClasses',
        component: () => import('@/views/TeachingClasses.vue'),
        meta: { title: '教学班管理', icon: 'School' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
