package com.example.hello.controller;

import com.example.hello.common.Result;
import com.example.hello.pojo.Dept;
import com.example.hello.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/depts")
public class DeptController {
    
    @Autowired
    private DeptService deptService;

    @GetMapping
    public Result list() {
        try {
            return Result.success(deptService.list());
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping
    public Result add(@RequestBody Dept dept) {
        try {
            deptService.add(dept);
            return Result.success();
        } catch (Exception e) {
            String msg = e.getMessage();
            Integer code = switch (msg) {
                case "部门名称已存在" -> 1001;
                case "部门名称长度不符合要求(2-10位)" -> 1002;
                default -> 0;
            };
            return Result.error(code, msg);
        }
    }

    @PutMapping
    public Result update(@RequestBody Dept dept) {
        try {
            deptService.update(dept);
            return Result.success();
        } catch (Exception e) {
            String msg = e.getMessage();
            Integer code = switch (msg) {
                case "部门名称已存在" -> 1001;
                case "部门名称长度不符合要求(2-10位)" -> 1002;
                case "部门不存在" -> 1003;
                default -> 0;
            };
            return Result.error(code, msg);
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        try {
            deptService.delete(id);
            return Result.success();
        } catch (Exception e) {
            String msg = e.getMessage();
            Integer code = switch (msg) {
                case "部门不存在" -> 1003;
                case "部门下存在员工，无法删除" -> 1004;
                default -> 0;
            };
            return Result.error(code, msg);
        }
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        try {
            return Result.success(deptService.getById(id));
        } catch (Exception e) {
            String msg = e.getMessage();
            Integer code = "部门不存在".equals(msg) ? 1003 : 0;
            return Result.error(code, msg);
        }
    }
}
