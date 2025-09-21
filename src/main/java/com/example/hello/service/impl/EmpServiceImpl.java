package com.example.hello.service.impl;

import com.example.hello.mapper.DeptMapper;
import com.example.hello.mapper.EmpMapper;
import com.example.hello.mapper.EmpExprMapper;
import com.example.hello.pojo.Emp;
import com.example.hello.pojo.EmpExpr;
import com.example.hello.pojo.EmpQuery;
import com.example.hello.service.EmpService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;

@Service
public class EmpServiceImpl implements EmpService {
    
    @Autowired
    private EmpMapper empMapper;
    
    @Autowired
    private DeptMapper deptMapper;
    
    @Autowired
    private EmpExprMapper empExprMapper;

    @Override
    public List<Emp> list(EmpQuery query) {
        // 构建查询条件
        List<String> conditions = new ArrayList<>();
        
        if (query.getName() != null && !query.getName().isEmpty()) {
            conditions.add("e.name LIKE CONCAT('%', '" + query.getName() + "', '%')");
        }
        if (query.getGender() != null) {
            conditions.add("e.gender = " + query.getGender());
        }
        if (query.getBegin() != null) {
            conditions.add("e.entry_date >= '" + query.getBegin() + "'");
        }
        if (query.getEnd() != null) {
            conditions.add("e.entry_date <= '" + query.getEnd() + "'");
        }
        
        String conditionSql = conditions.isEmpty() ? "" : "AND " + String.join(" AND ", conditions);
        
        // 设置分页
        PageHelper.startPage(query.getPage() != null ? query.getPage() : 1, 
                           query.getPageSize() != null ? query.getPageSize() : 10);
        
        return empMapper.list(conditionSql);
    }

    @Override
    @Transactional
    public void add(Emp emp) {
        validateEmp(emp);
        
        // 检查部门是否存在
        if (emp.getDeptId() != null && deptMapper.getById(emp.getDeptId()) == null) {
            throw new RuntimeException("部门不存在");
        }
        
        // 保存员工基本信息
        empMapper.insert(emp);
        
        // 保存工作经历
        if (emp.getExperiences() != null && !emp.getExperiences().isEmpty()) {
            for (EmpExpr expr : emp.getExperiences()) {
                expr.setEmpId(emp.getId());
                empExprMapper.insert(expr);
            }
        }
    }

    @Override
    @Transactional
    public void update(Emp emp) {
        // 检查员工是否存在
        if (empMapper.getById(emp.getId()) == null) {
            throw new RuntimeException("员工不存在");
        }
        
        validateEmp(emp);
        
        // 检查部门是否存在
        if (emp.getDeptId() != null && deptMapper.getById(emp.getDeptId()) == null) {
            throw new RuntimeException("部门不存在");
        }
        
        // 更新员工基本信息
        empMapper.update(emp);
        
        // 更新工作经历：先删除原有的，再添加新的
        if (emp.getExperiences() != null) {
            empExprMapper.deleteByEmpId(emp.getId());
            for (EmpExpr expr : emp.getExperiences()) {
                expr.setEmpId(emp.getId());
                empExprMapper.insert(expr);
            }
        }
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        // 检查员工是否存在
        if (empMapper.getById(id) == null) {
            throw new RuntimeException("员工不存在");
        }
        
        // 删除员工工作经历
        empExprMapper.deleteByEmpId(id);
        // 删除员工
        empMapper.deleteById(id);
    }
    
    @Override
    @Transactional
    public void deleteBatch(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        
        // 删除员工工作经历
        for (Integer id : ids) {
            empExprMapper.deleteByEmpId(id);
        }
        // 批量删除员工
        empMapper.deleteBatch(ids);
    }

    @Override
    public Emp getById(Integer id) {
        Emp emp = empMapper.getById(id);
        if (emp == null) {
            throw new RuntimeException("员工不存在");
        }
        return emp;
    }
    
    private void validateEmp(Emp emp) {
        // 验证用户名
        if (emp.getUsername() == null || emp.getUsername().length() < 2 || emp.getUsername().length() > 20) {
            throw new RuntimeException("用户名长度不符合要求(2-20位)");
        }
        
        // 验证姓名
        if (emp.getName() == null || emp.getName().length() < 2 || emp.getName().length() > 10) {
            throw new RuntimeException("姓名长度不符合要求(2-10位)");
        }
        
        // 验证手机号
        if (emp.getPhone() == null || !emp.getPhone().matches("^1\\d{10}$")) {
            throw new RuntimeException("手机号格式不正确");
        }
        
        // 检查用户名唯一性
        Emp existingEmp = empMapper.getById(emp.getId());
        if (existingEmp == null || !existingEmp.getUsername().equals(emp.getUsername())) {
            if (empMapper.countByUsername(emp.getUsername()) > 0) {
                throw new RuntimeException("用户名已存在");
            }
        }
        
        // 检查手机号唯一性
        if (existingEmp == null || !existingEmp.getPhone().equals(emp.getPhone())) {
            if (empMapper.countByPhone(emp.getPhone()) > 0) {
                throw new RuntimeException("手机号已存在");
            }
        }
    }
}
