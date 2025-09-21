<template>
  <div class="employee-container">
    <!-- 搜索区域 -->
    <div class="search-area">
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="姓名">
          <el-input
            v-model="searchForm.name"
            placeholder="请输入员工姓名"
            clearable
            @keyup.enter="handleSearch"
          />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="searchForm.gender" placeholder="请选择" clearable>
            <el-option
              v-for="item in genderOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="入职时间">
          <el-date-picker
            v-model="searchForm.entryDateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 工具栏 -->
    <div class="toolbar">
      <el-button type="primary" @click="handleAdd">
        <el-icon><Plus /></el-icon>新增员工
      </el-button>
      <el-button type="danger" :disabled="!selectedIds.length" @click="handleBatchDelete">
        <el-icon><Delete /></el-icon>批量删除
      </el-button>
    </div>

    <!-- 数据表格 -->
    <el-table
      :data="tableData"
      style="width: 100%"
      v-loading="loading"
      @selection-change="handleSelectionChange"
      border
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="序号" width="60" align="center" />
      <el-table-column prop="name" label="姓名" width="100" align="center" />
      <el-table-column prop="gender" label="性别" width="80" align="center">
        <template #default="scope">
          {{ scope.row.gender === 1 ? '男' : '女' }}
        </template>
      </el-table-column>
      <el-table-column prop="image" label="头像" width="100" align="center">
        <template #default="scope">
          <el-image
            v-if="scope.row.image"
            :src="scope.row.image"
            :preview-src-list="[scope.row.image]"
            fit="cover"
            class="avatar"
          >
            <template #error>
              <el-icon><UserFilled /></el-icon>
            </template>
          </el-image>
          <el-icon v-else><UserFilled /></el-icon>
        </template>
      </el-table-column>
      <el-table-column prop="deptName" label="所属部门" width="120" align="center" />
      <el-table-column prop="position" label="职位" width="120" align="center">
        <template #default="scope">
          {{ getPositionLabel(scope.row.position) }}
        </template>
      </el-table-column>
      <el-table-column prop="entryDate" label="入职日期" width="120" align="center" />
      <el-table-column prop="phone" label="手机号" width="120" align="center" />
      <el-table-column prop="salary" label="薪资" width="120" align="center">
        <template #default="scope">
          {{ scope.row.salary ? `￥${scope.row.salary}` : '--' }}
        </template>
      </el-table-column>
      <el-table-column prop="updateTime" label="最后修改时间" width="180" align="center" />
      <el-table-column label="操作" width="150" fixed="right" align="center">
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

    <!-- 分页 -->
    <div class="pagination">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 50, 100]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        :pager-count="7"
        small
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!-- 新增/编辑对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="800px"
      @close="resetForm"
    >
      <el-form
        ref="empFormRef"
        :model="empForm"
        :rules="rules"
        label-width="100px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input
                v-model="empForm.username"
                placeholder="请输入用户名，2-20个字符"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input
                v-model="empForm.name"
                placeholder="请输入姓名，2-10个汉字"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="empForm.gender" placeholder="请选择">
                <el-option
                  v-for="item in genderOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input
                v-model="empForm.phone"
                placeholder="请输入11位手机号"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="职位" prop="position">
              <el-select v-model="empForm.position" placeholder="请选择">
                <el-option
                  v-for="item in positionOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="薪资" prop="salary">
              <el-input-number
                v-model="empForm.salary"
                :min="0"
                :precision="2"
                :step="1000"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="所属部门" prop="deptId">
              <el-select v-model="empForm.deptId" placeholder="请选择">
                <el-option
                  v-for="dept in deptList"
                  :key="dept.id"
                  :label="dept.name"
                  :value="dept.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入职日期" prop="entryDate">
              <el-date-picker
                v-model="empForm.entryDate"
                type="date"
                placeholder="请选择日期"
                value-format="YYYY-MM-DD"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="头像" prop="image">
          <el-upload
            class="avatar-uploader"
            action="#"
            :show-file-list="false"
            :before-upload="beforeAvatarUpload"
            :http-request="handleAvatarUpload"
          >
            <img v-if="empForm.image" :src="empForm.image" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
          <div class="upload-tip">
            建议上传200*200或300*300尺寸的图片，大小不超过2MB
          </div>
        </el-form-item>

        <el-form-item label="工作经历">
          <div class="work-history">
            <el-button type="primary" @click="addWorkHistory">
              <el-icon><Plus /></el-icon>新增工作经历
            </el-button>
            
            <div v-for="(item, index) in workHistory" :key="index" class="work-history-item">
              <el-row :gutter="20">
                <el-col :span="10">
                  <el-form-item label="时间" label-width="60px">
                    <el-date-picker
                      v-model="item.dateRange"
                      type="daterange"
                      range-separator="至"
                      start-placeholder="开始日期"
                      end-placeholder="结束日期"
                      value-format="YYYY-MM-DD"
                      style="width: 100%"
                      @change="handleDateRangeChange($event, item)"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="公司" label-width="60px">
                    <el-input v-model="item.company" placeholder="请输入公司名称" />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="职位" label-width="60px">
                    <el-input v-model="item.position" placeholder="请输入职位" />
                  </el-form-item>
                </el-col>
                <el-col :span="2" class="delete-btn">
                  <el-button type="danger" link @click="removeWorkHistory(index)">
                    删除
                  </el-button>
                </el-col>
              </el-row>
            </div>
          </div>
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
import { Plus, Delete, UserFilled } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { listEmps, addEmp, updateEmp, deleteEmp, positionOptions, genderOptions } from '@/api/emp'
import { listDepts } from '@/api/dept'
import { uploadFile } from '@/api/upload'

const searchForm = ref({
  name: '',
  gender: '',
  entryDateRange: []
})

const tableData = ref([])
const loading = ref(false)
const selectedIds = ref([])

const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const deptList = ref([])

const dialogVisible = ref(false)
const dialogTitle = ref('')
const empFormRef = ref(null)
const empForm = ref({
  id: null,
  username: '',
  name: '',
  gender: '',
  phone: '',
  position: '',
  salary: '',
  image: '',
  entryDate: '',
  deptId: ''
})

// 工作经历
const workHistory = ref([])

// 新增工作经历
const addWorkHistory = () => {
  workHistory.value.push({
    dateRange: [], // 使用 dateRange 存储日期范围
    startDate: '', // 实际的开始日期
    endDate: '',   // 实际的结束日期
    company: '',
    position: ''
  })
}

// 删除工作经历
const removeWorkHistory = (index) => {
  workHistory.value.splice(index, 1)
}

// 处理日期范围变化
const handleDateRangeChange = (dates, item) => {
  if (dates) {
    [item.startDate, item.endDate] = dates
  } else {
    item.startDate = ''
    item.endDate = ''
  }
}

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度为2-20个字符', trigger: 'blur' },
    { pattern: /^[a-zA-Z]+$/, message: '用户名只能包含字母', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '姓名长度为2-10个字符', trigger: 'blur' },
    { pattern: /^[\u4e00-\u9fa5]+$/, message: '姓名只能包含汉字', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^\d{11}$/, message: '手机号必须是11位数字', trigger: 'blur' }
  ],
  position: [
    { required: false, message: '请选择职位', trigger: 'change' }
  ],
  salary: [
    { required: false, pattern: /^\d+$/, message: '薪资必须是数字', trigger: 'blur' }
  ],
  deptId: [
    { required: false, message: '请选择所属部门', trigger: 'change' }
  ],
  entryDate: [
    { required: false, message: '请选择入职日期', trigger: 'change' }
  ]
}

const getPositionLabel = (value) => {
  const option = positionOptions.find(item => item.value === value)
  return option ? option.label : '--'
}

const getDeptList = async () => {
  try {
    const res = await listDepts()
    deptList.value = res.data
  } catch (error) {
    console.error('获取部门列表失败:', error)
  }
}

const getEmpList = async () => {
  try {
    loading.value = true
    const params = {
      page: currentPage.value,
      pageSize: pageSize.value,
      name: searchForm.value.name,
      gender: searchForm.value.gender,
      begin: searchForm.value.entryDateRange?.[0],
      end: searchForm.value.entryDateRange?.[1]
    }
    const res = await listEmps(params)
    tableData.value = res.data.rows
    total.value = res.data.total
  } catch (error) {
    console.error('获取员工列表失败:', error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  currentPage.value = 1
  getEmpList()
}

const resetSearch = () => {
  searchForm.value = {
    name: '',
    gender: '',
    entryDateRange: []
  }
  handleSearch()
}

const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map(item => item.id)
}

const handleAdd = () => {
  dialogTitle.value = '新增员工'
  dialogVisible.value = true
  empForm.value = {
    id: null,
    username: '',
    name: '',
    gender: '',
    phone: '',
    position: '',
    salary: '',
    image: '',
    entryDate: '',
    deptId: ''
  }
  workHistory.value = []
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑员工'
  dialogVisible.value = true
  empForm.value = { ...row }
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该员工吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(async () => {
      try {
        await deleteEmp(row.id)
        ElMessage.success('删除成功')
        getEmpList()
      } catch (error) {
        console.error('删除失败:', error)
      }
    })
    .catch(() => {})
}

const handleBatchDelete = () => {
  if (!selectedIds.value.length) {
    ElMessage.warning('请选择要删除的员工')
    return
  }
  ElMessageBox.confirm(`确定要删除选中的${selectedIds.value.length}名员工吗？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(async () => {
      try {
        await Promise.all(selectedIds.value.map(id => deleteEmp(id)))
        ElMessage.success('删除成功')
        getEmpList()
      } catch (error) {
        console.error('删除失败:', error)
      }
    })
    .catch(() => {})
}

const submitForm = async () => {
  if (!empFormRef.value) return
  
  await empFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (empForm.value.id) {
          await updateEmp(empForm.value)
          ElMessage.success('修改成功')
        } else {
          await addEmp(empForm.value)
          ElMessage.success('新增成功')
        }
        dialogVisible.value = false
        getEmpList()
      } catch (error) {
        console.error('操作失败:', error)
      }
    }
  })
}

const resetForm = () => {
  if (empFormRef.value) {
    empFormRef.value.resetFields()
  }
  empForm.value = {
    id: null,
    username: '',
    name: '',
    gender: '',
    phone: '',
    position: '',
    salary: '',
    image: '',
    entryDate: '',
    deptId: ''
  }
  workHistory.value = []
}

const beforeAvatarUpload = (file) => {
  const isJPG = file.type === 'image/jpeg'
  const isPNG = file.type === 'image/png'
  const isLt2M = file.size / 1024 / 1024 < 2

  if (!isJPG && !isPNG) {
    ElMessage.error('头像图片只能是 JPG 或 PNG 格式!')
    return false
  }
  if (!isLt2M) {
    ElMessage.error('头像图片大小不能超过 2MB!')
    return false
  }
  return true
}

const handleAvatarUpload = async (options) => {
  try {
    const res = await uploadFile(options.file)
    if (res.code === 1) {
      empForm.value.image = res.data
      ElMessage.success('上传成功')
    } else {
      ElMessage.error(res.msg || '上传失败')
    }
  } catch (error) {
    console.error('上传失败:', error)
    ElMessage.error('上传失败')
  }
}

const handleSizeChange = (val) => {
  pageSize.value = val
  getEmpList()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  getEmpList()
}

onMounted(() => {
  getDeptList()
  getEmpList()
})
</script>

<style scoped>
.employee-container {
  padding: 20px;
}

.search-area {
  background-color: #fff;
  padding: 20px;
  border-radius: 4px;
  margin-bottom: 20px;
}

.toolbar {
  margin-bottom: 20px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.avatar-uploader {
  text-align: center;
}

.avatar {
  width: 100px;
  height: 100px;
  border-radius: 4px;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  text-align: center;
  border: 1px dashed #d9d9d9;
  border-radius: 4px;
  cursor: pointer;
  line-height: 100px;
}

.avatar-uploader-icon:hover {
  border-color: #409EFF;
}

.upload-tip {
  font-size: 12px;
  color: #999;
  margin-top: 5px;
}

/* 工作经历相关样式 */
.work-history {
  margin-top: 10px;
  background-color: #f8f9fa;
  padding: 15px;
  border-radius: 4px;
}

.work-history-item {
  margin-top: 12px;
  padding: 10px;
  background-color: #fff;
  border-radius: 4px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);
}

.work-history-item:first-child {
  margin-top: 15px;
}

.delete-btn {
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 调整工作经历表单项的样式 */
.work-history :deep(.el-form-item) {
  margin-bottom: 0;
}

.work-history :deep(.el-form-item__label) {
  font-size: 13px;
  color: #606266;
}

.work-history :deep(.el-form-item__content) {
  font-size: 13px;
}

:deep(.el-table) {
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
}

:deep(.el-button--link) {
  padding: 0 8px;
}

:deep(.el-table .cell) {
  padding: 0 12px;
}

:deep(.el-table__header) {
  font-weight: bold;
}

:deep(.el-button + .el-button) {
  margin-left: 12px;
}
</style>