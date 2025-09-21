package com.example.hello.controller;

import com.example.hello.common.Result;
import com.example.hello.mapper.EmpMapper;
import com.example.hello.pojo.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private EmpMapper empMapper;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @PostMapping("/reset-password/{username}")
    public Result resetPassword(@PathVariable String username, @RequestBody String newPassword) {
        try {
            // 查找用户
            Emp emp = empMapper.getByUsername(username);
            if (emp == null) {
                return Result.error("用户不存在");
            }
            
            // 使用BCrypt加密新密码
            String encodedPassword = passwordEncoder.encode(newPassword);
            
            // 更新密码
            emp.setPassword(encodedPassword);
            empMapper.updatePassword(emp);
            
            return Result.success();
        } catch (Exception e) {
            return Result.error("密码重置失败：" + e.getMessage());
        }
    }
}


