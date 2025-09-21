package com.example.hello.service;

import com.example.hello.pojo.EmpExpr;
import java.util.List;

public interface EmpExprService {
    List<EmpExpr> listByEmpId(Integer empId);
    
    void add(Integer empId, EmpExpr empExpr);
    
    void update(Integer empId, Integer id, EmpExpr empExpr);
    
    void delete(Integer empId, Integer id);
}
