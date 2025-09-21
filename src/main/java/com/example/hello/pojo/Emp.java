package com.example.hello.pojo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Emp {
    private Integer id;
    private String username;
    private String name;
    private Integer gender;
    private String phone;
    private Integer position;
    private BigDecimal salary;
    private String image;
    private LocalDate entryDate;
    private Integer deptId;
    private String deptName;
    private String password;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer isDeleted;
    
    // 工作经历列表
    private List<EmpExpr> experiences;
}
