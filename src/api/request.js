import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '@/router'

// 创建axios实例
const service = axios.create({
  baseURL: '/api', // 通过代理访问API
  timeout: 5000 // 请求超时时间
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 从localStorage获取token
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.token = token
    }
    return config
  },
  error => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code !== 1) {
      ElMessage.error(res.msg || '操作失败')
      return Promise.reject(new Error(res.msg || '操作失败'))
    }
    return res
  },
  error => {
    console.error('请求错误:', error)
    if (error.response?.status === 401) {
      // 清除本地存储的token和用户信息
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
      // 跳转到登录页
      router.push('/login')
      ElMessage.error('登录已过期，请重新登录')
    } else {
      ElMessage.error(error.response?.data?.msg || error.message || '请求失败')
    }
    return Promise.reject(error)
  }
)

export default service