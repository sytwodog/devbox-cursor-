import request from './request'

// 查询所有部门
export function listDepts() {
  return request({
    url: '/depts',
    method: 'get'
  })
}

// 根据ID查询部门
export function getDeptById(id) {
  return request({
    url: `/depts/${id}`,
    method: 'get'
  })
}

// 新增部门
export function addDept(data) {
  return request({
    url: '/depts',
    method: 'post',
    data
  })
}

// 修改部门
export function updateDept(data) {
  return request({
    url: '/depts',
    method: 'put',
    data
  })
}

// 删除部门
export function deleteDept(id) {
  return request({
    url: `/depts/${id}`,
    method: 'delete'
  })
}
