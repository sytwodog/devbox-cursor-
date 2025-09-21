import request from './request'

// 员工列表查询
export function listEmps(params) {
  return request({
    url: '/emps',
    method: 'get',
    params
  })
}

// 根据ID查询员工
export function getEmpById(id) {
  return request({
    url: `/emps/${id}`,
    method: 'get'
  })
}

// 新增员工
export function addEmp(data) {
  return request({
    url: '/emps',
    method: 'post',
    data
  })
}

// 修改员工
export function updateEmp(data) {
  return request({
    url: '/emps',
    method: 'put',
    data
  })
}

// 删除员工
export function deleteEmp(id) {
  return request({
    url: `/emps/${id}`,
    method: 'delete'
  })
}

// 职位选项
export const positionOptions = [
  { label: '班主任', value: 1 },
  { label: '讲师', value: 2 },
  { label: '学工主管', value: 3 },
  { label: '教研主管', value: 4 },
  { label: '咨询师', value: 5 }
]

// 性别选项
export const genderOptions = [
  { label: '男', value: 1 },
  { label: '女', value: 2 }
]
