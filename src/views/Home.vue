<template>
  <div class="home">
    <!-- 欢迎区域 -->
    <div class="welcome-section">
      <div class="welcome-info">
        <h1>欢迎使用智学云帆教学管理系统</h1>
        <p class="welcome-text">{{ getCurrentTime() }}</p>
      </div>
    </div>

    <!-- 数据概览卡片 -->
    <el-row :gutter="20" class="data-overview">
      <el-col :span="6">
        <el-card shadow="hover" class="data-card">
          <template #header>
            <div class="card-header">
              <el-icon><User /></el-icon>
              <span>在读学员数</span>
            </div>
          </template>
          <div class="card-number">2,361</div>
          <div class="card-footer">
            <span class="trend-up">↑12.5%</span>
            <span class="trend-text">较上月</span>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card shadow="hover" class="data-card">
          <template #header>
            <div class="card-header">
              <el-icon><Reading /></el-icon>
              <span>正在开班数</span>
            </div>
          </template>
          <div class="card-number">16</div>
          <div class="card-footer">
            <span class="trend-up">↑2</span>
            <span class="trend-text">较上月</span>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card shadow="hover" class="data-card">
          <template #header>
            <div class="card-header">
              <el-icon><OfficeBuilding /></el-icon>
              <span>部门数量</span>
            </div>
          </template>
          <div class="card-number">8</div>
          <div class="card-footer">
            <span class="trend-stable">-</span>
            <span class="trend-text">较上月</span>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card shadow="hover" class="data-card">
          <template #header>
            <div class="card-header">
              <el-icon><UserFilled /></el-icon>
              <span>教职工数量</span>
            </div>
          </template>
          <div class="card-number">126</div>
          <div class="card-footer">
            <span class="trend-up">↑3</span>
            <span class="trend-text">较上月</span>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 快捷功能区 -->
    <div class="quick-access">
      <h2 class="section-title">快捷功能</h2>
      <el-row :gutter="20">
        <el-col :span="6" v-for="(item, index) in quickAccessItems" :key="index">
          <el-card shadow="hover" class="quick-card" @click="handleQuickAccess(item)">
            <el-icon class="quick-icon"><component :is="item.icon" /></el-icon>
            <div class="quick-text">{{ item.title }}</div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <!-- 系统公告 -->
    <div class="notice-section">
      <h2 class="section-title">系统公告</h2>
      <el-card shadow="hover">
        <el-timeline>
          <el-timeline-item
            v-for="(notice, index) in notices"
            :key="index"
            :timestamp="notice.time"
            :type="notice.type"
          >
            {{ notice.content }}
          </el-timeline-item>
        </el-timeline>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import {
  User,
  Reading,
  OfficeBuilding,
  UserFilled,
  Plus,
  Document,
  Calendar,
  Bell
} from '@element-plus/icons-vue'

const router = useRouter()

// 获取当前时间和问候语
const getCurrentTime = () => {
  const now = new Date()
  const hour = now.getHours()
  let greeting = ''
  
  if (hour < 6) {
    greeting = '凌晨好'
  } else if (hour < 9) {
    greeting = '早上好'
  } else if (hour < 12) {
    greeting = '上午好'
  } else if (hour < 14) {
    greeting = '中午好'
  } else if (hour < 17) {
    greeting = '下午好'
  } else if (hour < 19) {
    greeting = '傍晚好'
  } else {
    greeting = '晚上好'
  }
  
  return `${greeting}，今天是 ${now.getFullYear()}年${now.getMonth() + 1}月${now.getDate()}日`
}

// 快捷功能项
const quickAccessItems = [
  { 
    title: '新增班级', 
    icon: 'Plus', 
    route: '/class-management',
    parentIndex: '2'  // 对应父菜单的 index
  },
  { 
    title: '学员注册', 
    icon: 'User', 
    route: '/student-management',
    parentIndex: '2'
  },
  { 
    title: '部门管理', 
    icon: 'OfficeBuilding', 
    route: '/department-management',
    parentIndex: '3'
  },
  { 
    title: '员工管理', 
    icon: 'UserFilled', 
    route: '/employee-management',
    parentIndex: '3'
  }
]

// 系统公告
const notices = [
  {
    content: '关于2024年春季班级招生工作的通知',
    time: '2024-03-15',
    type: 'primary'
  },
  {
    content: '系统将于本周日凌晨2点进行例行维护',
    time: '2024-03-14',
    type: 'warning'
  },
  {
    content: '新版教学管理系统正式上线',
    time: '2024-03-10',
    type: 'success'
  }
]

// 快捷功能点击处理
const handleQuickAccess = (item) => {
  // 使用编程式导航跳转到目标路由
  router.push(item.route)
}
</script>

<style scoped>
.home {
  padding: 20px;
}

.welcome-section {
  background: linear-gradient(135deg, #FFB6C1 0%, #FFC0CB 100%);
  border-radius: 12px;
  padding: 30px;
  margin-bottom: 24px;
  color: white;
  box-shadow: 0 4px 12px rgba(255, 182, 193, 0.2);
}

.welcome-section h1 {
  font-size: 28px;
  margin: 0;
  margin-bottom: 10px;
}

.welcome-text {
  font-size: 16px;
  opacity: 0.9;
}

.data-overview {
  margin-bottom: 24px;
}

.data-card {
  transition: all 0.3s;
}

.data-card:hover {
  transform: translateY(-5px);
}

.card-header {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #666;
}

.card-number {
  font-size: 28px;
  font-weight: bold;
  color: #FF69B4;
  margin: 10px 0;
}

.card-footer {
  font-size: 14px;
  color: #999;
}

.trend-up {
  color: #67C23A;
  margin-right: 5px;
}

.trend-down {
  color: #F56C6C;
  margin-right: 5px;
}

.trend-stable {
  color: #909399;
  margin-right: 5px;
}

.section-title {
  font-size: 20px;
  color: #666;
  margin-bottom: 20px;
  font-weight: normal;
}

.quick-access {
  margin-bottom: 24px;
}

.quick-card {
  cursor: pointer;
  text-align: center;
  padding: 20px;
  transition: all 0.3s;
}

.quick-card:hover {
  transform: translateY(-5px);
  background-color: #FFF0F5;
}

.quick-icon {
  font-size: 32px;
  color: #FF69B4;
  margin-bottom: 10px;
}

.quick-text {
  color: #666;
}

.notice-section {
  margin-bottom: 24px;
}

:deep(.el-timeline-item__node--primary) {
  background-color: #FF69B4;
}

:deep(.el-timeline-item__node--success) {
  background-color: #67C23A;
}

:deep(.el-timeline-item__node--warning) {
  background-color: #E6A23C;
}

:deep(.el-card) {
  border-radius: 8px;
  border: none;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

:deep(.el-card__header) {
  border-bottom: 1px solid #f0f0f0;
  padding: 15px 20px;
}

:deep(.el-timeline-item__content) {
  color: #666;
}

:deep(.el-timeline-item__timestamp) {
  color: #999;
}
</style>