<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-form">
        <h2 class="system-title">智学云帆-教学管理系统</h2>
        <el-form
          ref="loginFormRef"
          :model="loginForm"
          :rules="loginRules"
          label-position="top"
        >
          <el-form-item prop="username">
            <el-input
              v-model="loginForm.username"
              placeholder="请输入员工用户名"
              prefix-icon="User"
            />
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
              prefix-icon="Lock"
              show-password
              @keyup.enter="handleLogin"
            />
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              :loading="loading"
              class="login-button"
              @click="handleLogin"
            >
              登录
            </el-button>
            <el-button class="login-button" @click="resetForm">
              取消
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'
import request from '@/api/request'

const router = useRouter()
const loading = ref(false)
const loginFormRef = ref(null)

const loginForm = ref({
  username: '',
  password: ''
})

const loginRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度为2-20个字符', trigger: 'blur' },
    { pattern: /^[a-zA-Z0-9_]+$/, message: '用户名只能包含字母、数字和下划线', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度为6-20个字符', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  if (!loginFormRef.value) return

  try {
    // 表单验证
    await loginFormRef.value.validate()
    
    // 如果用户名或密码为空，直接返回
    if (!loginForm.value.username.trim() || !loginForm.value.password.trim()) {
      ElMessage.warning('请输入用户名和密码')
      return
    }

    loading.value = true

    const res = await request({
      url: '/login',
      method: 'post',
      data: loginForm.value
    })

    if (res.code === 1 && res.data) {
      // 保存token和用户信息
      localStorage.setItem('token', res.data.token)
      localStorage.setItem('userInfo', JSON.stringify({
        id: res.data.id,
        username: res.data.username,
        name: res.data.name
      }))

      ElMessage.success('登录成功，欢迎回来')
      router.push('/') // 跳转到首页
    } else {
      ElMessage.error('用户名或密码错误')
    }
  } catch (error) {
    console.error('登录失败:', error)
    if (error.response?.status === 401) {
      ElMessage.error('用户名或密码错误')
    } else if (error.message?.includes('Network Error')) {
      ElMessage.error('网络连接失败，请检查网络设置')
    } else {
      ElMessage.error('登录失败，请稍后重试')
    }
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  if (loginFormRef.value) {
    loginFormRef.value.resetFields()
  }
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f0f2f5;
  background-image: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

.login-box {
  width: 400px;
  padding: 40px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.system-title {
  text-align: center;
  font-family: KaiTi;
  font-size: 30px;
  color: #333;
  margin-bottom: 40px;
}

.login-button {
  width: 100%;
  margin-top: 10px;
}

:deep(.el-input__wrapper) {
  background-color: #f5f7fa;
}

:deep(.el-input__inner) {
  height: 40px;
}

:deep(.el-form-item__label) {
  font-weight: normal;
}

:deep(.el-button--primary) {
  margin-bottom: 16px;
}
</style>