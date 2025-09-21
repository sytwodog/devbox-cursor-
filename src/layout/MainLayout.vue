<template>
  <el-container class="layout-container">
    <!-- 顶部标题栏 -->
    <el-header height="60px" class="header">
      <div class="header-title">智学云帆-教学管理系统</div>
      <div class="header-user">
        <el-link :underline="false" class="logout-link" @click="handleLogout">
          <el-icon class="logout-icon"><SwitchButton /></el-icon>
          退出登录【{{ userInfo.name }}】
        </el-link>
      </div>
    </el-header>

    <el-container>
      <!-- 左侧菜单栏 -->
      <el-aside width="220px" class="aside">
        <el-menu
          :default-active="activeMenu"
          class="el-menu-vertical"
          router
          background-color="#FFF0F5"
          text-color="#666666"
          active-text-color="#FF69B4"
        >
          <el-menu-item index="/">
            <el-icon><HomeFilled /></el-icon>
            <span>首页</span>
          </el-menu-item>

          <el-sub-menu index="2">
            <template #title>
              <el-icon><School /></el-icon>
              <span>班级学员管理</span>
            </template>
            <el-menu-item index="/class-management">
              <el-icon><Reading /></el-icon>
              <span>班级管理</span>
            </el-menu-item>
            <el-menu-item index="/student-management">
              <el-icon><User /></el-icon>
              <span>学员管理</span>
            </el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="3">
            <template #title>
              <el-icon><Platform /></el-icon>
              <span>系统信息管理</span>
            </template>
            <el-menu-item index="/department-management">
              <el-icon><OfficeBuilding /></el-icon>
              <span>部门管理</span>
            </el-menu-item>
            <el-menu-item index="/employee-management">
              <el-icon><UserFilled /></el-icon>
              <span>员工管理</span>
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>

      <!-- 右侧内容区域 -->
      <el-main class="main">
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessageBox, ElMessage } from 'element-plus'
import { 
  HomeFilled,
  School,
  Reading,
  User,
  UserFilled,
  Platform,
  OfficeBuilding,
  SwitchButton
} from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const activeMenu = ref(route.path)
const userInfo = ref({
  name: '未知用户'
})

// 监听路由变化，更新菜单激活状态
watch(() => route.path, (newPath) => {
  activeMenu.value = newPath
})

// 获取用户信息
onMounted(() => {
  const storedUserInfo = localStorage.getItem('userInfo')
  if (storedUserInfo) {
    userInfo.value = JSON.parse(storedUserInfo)
  }
})

// 退出登录
const handleLogout = () => {
  ElMessageBox.confirm(
    '确定要退出登录吗？',
    '提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  )
    .then(() => {
      // 清除本地存储的token和用户信息
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
      
      // 显示退出成功提示
      ElMessage.success('退出成功')
      
      // 跳转到登录页
      router.push('/login')
    })
    .catch(() => {
      // 用户点击取消，不做任何操作
    })
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.header {
  background-color: #FFE4E1;
  border-bottom: 1px solid #FFD9D9;
  box-shadow: 0 1px 4px rgba(255, 192, 203, 0.15);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.header-title {
  font-family: KaiTi, serif;
  font-size: 30px;
  color: #FF69B4;
  font-weight: bold;
  letter-spacing: 1px;
}

.header-user {
  display: flex;
  align-items: center;
}

.logout-link {
  font-size: 14px;
  color: #FF69B4;
  transition: all 0.3s;
  display: flex;
  align-items: center;
  gap: 5px;
  cursor: pointer;
}

.logout-link:hover {
  color: #FF1493;
}

.logout-icon {
  margin-right: 4px;
}

.aside {
  background-color: #FFF0F5;
  box-shadow: 2px 0 6px rgba(255, 192, 203, 0.15);
  transition: width 0.3s;
}

.el-menu-vertical {
  border-right: none;
}

.el-menu-vertical:not(.el-menu--collapse) {
  width: 220px;
}

/* 菜单项样式 */
:deep(.el-menu-item) {
  height: 50px;
  line-height: 50px;
  padding-left: 20px !important;
}

:deep(.el-menu-item:hover) {
  background-color: #FFE4E1 !important;
  color: #FF69B4 !important;
}

:deep(.el-menu-item.is-active) {
  background-color: #FFD9D9 !important;
  color: #FF69B4 !important;
}

:deep(.el-sub-menu__title) {
  height: 50px;
  line-height: 50px;
  padding-left: 20px !important;
}

:deep(.el-sub-menu__title:hover) {
  background-color: #FFE4E1 !important;
  color: #FF69B4 !important;
}

/* 子菜单背景色 */
:deep(.el-menu--inline) {
  background-color: #FFF5F5 !important;
}

/* 图标样式 */
:deep(.el-icon) {
  margin-right: 10px;
  font-size: 18px;
  color: inherit;
}

.main {
  background-color: #FFF9F9;
  padding: 20px;
}

/* 内容区域的阴影效果 */
:deep(.el-main) {
  box-shadow: inset 0 0 15px rgba(255, 192, 203, 0.1);
}

/* 菜单hover时图标颜色 */
:deep(.el-menu-item:hover .el-icon),
:deep(.el-menu-item.is-active .el-icon),
:deep(.el-sub-menu__title:hover .el-icon) {
  color: #FF69B4;
}

/* 调整子菜单图标和文字的间距 */
:deep(.el-menu--inline .el-menu-item) {
  padding-left: 48px !important;
}
</style>