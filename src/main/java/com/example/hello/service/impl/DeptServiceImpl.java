package com.example.hello.service.impl;

import com.example.hello.mapper.DeptMapper;
import com.example.hello.pojo.Dept;
import com.example.hello.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }

    @Override
    @Transactional
    public void add(Dept dept) {
        // 检查部门名称长度
        if (dept.getName() == null || dept.getName().length() < 2 || dept.getName().length() > 10) {
            throw new RuntimeException("部门名称长度不符合要求(2-10位)");
        }
        
        // 检查部门名称是否已存在
        if (deptMapper.countByName(dept.getName()) > 0) {
            throw new RuntimeException("部门名称已存在");
        }
        
        deptMapper.insert(dept);
    }

    @Override
    @Transactional
    public void update(Dept dept) {
        // 检查部门是否存在
        if (deptMapper.getById(dept.getId()) == null) {
            throw new RuntimeException("部门不存在");
        }
        
        // 检查部门名称长度
        if (dept.getName() == null || dept.getName().length() < 2 || dept.getName().length() > 10) {
            throw new RuntimeException("部门名称长度不符合要求(2-10位)");
        }
        
        // 检查新名称是否与其他部门重复
        Dept existingDept = deptMapper.getById(dept.getId());
        if (!existingDept.getName().equals(dept.getName()) && deptMapper.countByName(dept.getName()) > 0) {
            throw new RuntimeException("部门名称已存在");
        }
        
        deptMapper.update(dept);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        // 检查部门是否存在
        if (deptMapper.getById(id) == null) {
            throw new RuntimeException("部门不存在");
        }
        
        // TODO: 如果需要检查部门下是否存在员工，需要添加相应的检查逻辑
        
        deptMapper.deleteById(id);
    }

    @Override
    public Dept getById(Integer id) {
        Dept dept = deptMapper.getById(id);
        if (dept == null) {
            throw new RuntimeException("部门不存在");
        }
        return dept;
    }
}
