<template>
  <div class="department-container">
    <!-- 顶部操作区 -->
    <div class="top-actions">
      <el-button type="primary" @click="handleAdd">
        <el-icon><Plus /></el-icon>新增部门
      </el-button>
    </div>

    <!-- 数据表格 -->
    <el-table 
      :data="deptList" 
      style="width: 100%" 
      v-loading="loading"
      border
    >
      <el-table-column 
        type="index" 
        label="序号" 
        width="80" 
        align="center"
      />
      <el-table-column 
        prop="name" 
        label="部门名称" 
        width="180" 
        align="center"
      />
      <el-table-column 
        prop="createTime" 
        label="创建时间" 
        width="200" 
        align="center"
      >
        <template #default="scope">
          {{ formatDateTime(scope.row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column 
        prop="updateTime" 
        label="修改时间" 
        width="200" 
        align="center"
      >
        <template #default="scope">
          {{ formatDateTime(scope.row.updateTime) }}
        </template>
      </el-table-column>
      <el-table-column 
        label="操作" 
        width="200" 
        fixed="right"
        align="center"
      >
        <template #default="scope">
          <el-button type="primary" link @click="handleEdit(scope.row)">
            编辑
          </el-button>
          <el-button type="danger" link @click="handleDelete(scope.row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增/编辑对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="500px"
      @close="resetForm"
    >
      <el-form
        ref="deptFormRef"
        :model="deptForm"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="部门名称" prop="name">
          <el-input
            v-model="deptForm.name"
            placeholder="请输入部门名称，长度为2-10位"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { listDepts, addDept, updateDept, deleteDept } from '@/api/dept'

// 数据列表
const deptList = ref([])
const loading = ref(false)

// 对话框相关
const dialogVisible = ref(false)
const dialogTitle = ref('')
const deptFormRef = ref(null)
const deptForm = ref({
  id: null,
  name: ''
})

// 表单校验规则
const rules = {
  name: [
    { required: true, message: '请输入部门名称', trigger: 'blur' },
    { min: 2, max: 10, message: '部门名称长度为2-10个字符', trigger: 'blur' }
  ]
}

// 格式化日期时间
const formatDateTime = (dateTimeStr) => {
  if (!dateTimeStr) return ''
  const date = new Date(dateTimeStr)
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const hours = String(date.getHours()).padStart(2, '0')
  const minutes = String(date.getMinutes()).padStart(2, '0')
  const seconds = String(date.getSeconds()).padStart(2, '0')
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
}

// 获取部门列表
const getDeptList = async () => {
  try {
    loading.value = true
    const res = await listDepts()
    deptList.value = res.data
  } catch (error) {
    console.error('获取部门列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 新增部门
const handleAdd = () => {
  dialogTitle.value = '新增部门'
  dialogVisible.value = true
  deptForm.value = { id: null, name: '' }
}

// 编辑部门
const handleEdit = (row) => {
  dialogTitle.value = '编辑部门'
  dialogVisible.value = true
  deptForm.value = { ...row }
}

// 删除部门
const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该部门吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(async () => {
      try {
        await deleteDept(row.id)
        ElMessage.success('删除成功')
        getDeptList()
      } catch (error) {
        console.error('删除失败:', error)
      }
    })
    .catch(() => {})
}

// 提交表单
const submitForm = async () => {
  if (!deptFormRef.value) return
  
  await deptFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (deptForm.value.id) {
          await updateDept(deptForm.value)
          ElMessage.success('修改成功')
        } else {
          await addDept(deptForm.value)
          ElMessage.success('新增成功')
        }
        dialogVisible.value = false
        getDeptList()
      } catch (error) {
        console.error('操作失败:', error)
      }
    }
  })
}

// 重置表单
const resetForm = () => {
  if (deptFormRef.value) {
    deptFormRef.value.resetFields()
  }
  deptForm.value = { id: null, name: '' }
}

// 页面加载时获取数据
onMounted(() => {
  getDeptList()
})
</script>

<style scoped>
.department-container {
  padding: 20px;
}

.top-actions {
  margin-bottom: 20px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

:deep(.el-table) {
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
}

:deep(.el-button--link) {
  padding: 0 8px;
}

/* 调整表格内容的样式 */
:deep(.el-table .cell) {
  padding: 0 12px;
}

:deep(.el-table__header) {
  font-weight: bold;
}

/* 调整操作按钮的间距 */
:deep(.el-button + .el-button) {
  margin-left: 12px;
}
</style>