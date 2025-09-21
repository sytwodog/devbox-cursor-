package com.example.hello.service;

import com.example.hello.pojo.Emp;
import com.example.hello.pojo.EmpQuery;
import java.util.List;

public interface EmpService {
    List<Emp> list(EmpQuery query);
    
    void add(Emp emp);
    
    void update(Emp emp);
    
    void delete(Integer id);
    
    void deleteBatch(List<Integer> ids);
    
    Emp getById(Integer id);
}
