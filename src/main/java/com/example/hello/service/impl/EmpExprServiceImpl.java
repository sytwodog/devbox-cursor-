package com.example.hello.service.impl;

import com.example.hello.mapper.EmpExprMapper;
import com.example.hello.mapper.EmpMapper;
import com.example.hello.pojo.EmpExpr;
import com.example.hello.service.EmpExprService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpExprServiceImpl implements EmpExprService {
    
    @Autowired
    private EmpExprMapper empExprMapper;
    
    @Autowired
    private EmpMapper empMapper;

    @Override
    public List<EmpExpr> listByEmpId(Integer empId) {
        // 检查员工是否存在
        if (empMapper.getById(empId) == null) {
            throw new RuntimeException("员工不存在");
        }
        
        return empExprMapper.listByEmpId(empId);
    }

    @Override
    @Transactional
    public void add(Integer empId, EmpExpr empExpr) {
        // 检查员工是否存在
        if (empMapper.getById(empId) == null) {
            throw new RuntimeException("员工不存在");
        }
        
        validateEmpExpr(empExpr);
        
        empExpr.setEmpId(empId);
        empExprMapper.insert(empExpr);
    }

    @Override
    @Transactional
    public void update(Integer empId, Integer id, EmpExpr empExpr) {
        // 检查员工是否存在
        if (empMapper.getById(empId) == null) {
            throw new RuntimeException("员工不存在");
        }
        
        // 检查工作经历是否存在
        if (empExprMapper.getById(id) == null) {
            throw new RuntimeException("工作经历不存在");
        }
        
        validateEmpExpr(empExpr);
        
        empExpr.setId(id);
        empExpr.setEmpId(empId);
        empExprMapper.update(empExpr);
    }

    @Override
    @Transactional
    public void delete(Integer empId, Integer id) {
        // 检查员工是否存在
        if (empMapper.getById(empId) == null) {
            throw new RuntimeException("员工不存在");
        }
        
        // 检查工作经历是否存在
        if (empExprMapper.getById(id) == null) {
            throw new RuntimeException("工作经历不存在");
        }
        
        empExprMapper.delete(empId, id);
    }
    
    private void validateEmpExpr(EmpExpr empExpr) {
        // 验证公司名称
        if (empExpr.getCompany() != null && 
            (empExpr.getCompany().length() < 2 || empExpr.getCompany().length() > 50)) {
            throw new RuntimeException("公司名称长度不符合要求(2-50位)");
        }
        
        // 验证职位名称
        if (empExpr.getPosition() != null && 
            (empExpr.getPosition().length() < 2 || empExpr.getPosition().length() > 50)) {
            throw new RuntimeException("职位名称长度不符合要求(2-50位)");
        }
    }
}
