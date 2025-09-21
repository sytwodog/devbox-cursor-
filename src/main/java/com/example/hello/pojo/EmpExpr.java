package com.example.hello.pojo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EmpExpr {
    private Integer id;
    private Integer empId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String company;
    private String position;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
