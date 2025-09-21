package com.example.hello.controller;

import com.example.hello.common.Result;
import com.example.hello.pojo.Emp;
import com.example.hello.pojo.EmpExpr;
import com.example.hello.pojo.EmpQuery;
import com.example.hello.service.EmpExprService;
import com.example.hello.service.EmpService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/emps")
public class EmpController {
    
    @Autowired
    private EmpService empService;
    
    @Autowired
    private EmpExprService empExprService;

    @GetMapping
    public Result list(EmpQuery query) {
        try {
            var emps = empService.list(query);
            var pageInfo = new PageInfo<>(emps);
            
            Map<String, Object> data = new HashMap<>();
            data.put("total", pageInfo.getTotal());
            data.put("rows", pageInfo.getList());
            
            return Result.success(data);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping
    public Result add(@RequestBody Emp emp) {
        try {
            empService.add(emp);
            return Result.success();
        } catch (Exception e) {
            String msg = e.getMessage();
            Integer code = switch (msg) {
                case "用户名已存在" -> 2001;
                case "手机号已存在" -> 2002;
                case "用户名长度不符合要求(2-20位)" -> 2003;
                case "姓名长度不符合要求(2-10位)" -> 2004;
                case "手机号格式不正确" -> 2005;
                case "部门不存在" -> 2008;
                default -> 0;
            };
            return Result.error(code, msg);
        }
    }

    @PutMapping
    public Result update(@RequestBody Emp emp) {
        try {
            empService.update(emp);
            return Result.success();
        } catch (Exception e) {
            String msg = e.getMessage();
            Integer code = switch (msg) {
                case "用户名已存在" -> 2001;
                case "手机号已存在" -> 2002;
                case "用户名长度不符合要求(2-20位)" -> 2003;
                case "姓名长度不符合要求(2-10位)" -> 2004;
                case "手机号格式不正确" -> 2005;
                case "员工不存在" -> 2006;
                case "部门不存在" -> 2008;
                default -> 0;
            };
            return Result.error(code, msg);
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        try {
            empService.delete(id);
            return Result.success();
        } catch (Exception e) {
            String msg = e.getMessage();
            Integer code = "员工不存在".equals(msg) ? 2006 : 0;
            return Result.error(code, msg);
        }
    }
    
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        try {
            empService.deleteBatch(ids);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        try {
            return Result.success(empService.getById(id));
        } catch (Exception e) {
            String msg = e.getMessage();
            Integer code = "员工不存在".equals(msg) ? 2006 : 0;
            return Result.error(code, msg);
        }
    }

    @GetMapping("/{empId}/experiences")
    public Result listExperiences(@PathVariable Integer empId) {
        try {
            return Result.success(empExprService.listByEmpId(empId));
        } catch (Exception e) {
            String msg = e.getMessage();
            Integer code = "员工不存在".equals(msg) ? 2006 : 0;
            return Result.error(code, msg);
        }
    }

    @PostMapping("/{empId}/experiences")
    public Result addExperience(@PathVariable Integer empId, @RequestBody EmpExpr empExpr) {
        try {
            empExprService.add(empId, empExpr);
            return Result.success();
        } catch (Exception e) {
            String msg = e.getMessage();
            Integer code = switch (msg) {
                case "员工不存在" -> 2006;
                default -> 0;
            };
            return Result.error(code, msg);
        }
    }

    @PutMapping("/{empId}/experiences/{id}")
    public Result updateExperience(@PathVariable Integer empId, 
                                 @PathVariable Integer id, 
                                 @RequestBody EmpExpr empExpr) {
        try {
            empExprService.update(empId, id, empExpr);
            return Result.success();
        } catch (Exception e) {
            String msg = e.getMessage();
            Integer code = switch (msg) {
                case "员工不存在" -> 2006;
                case "工作经历不存在" -> 2007;
                default -> 0;
            };
            return Result.error(code, msg);
        }
    }

    @DeleteMapping("/{empId}/experiences/{id}")
    public Result deleteExperience(@PathVariable Integer empId, @PathVariable Integer id) {
        try {
            empExprService.delete(empId, id);
            return Result.success();
        } catch (Exception e) {
            String msg = e.getMessage();
            Integer code = switch (msg) {
                case "员工不存在" -> 2006;
                case "工作经历不存在" -> 2007;
                default -> 0;
            };
            return Result.error(code, msg);
        }
    }
}
