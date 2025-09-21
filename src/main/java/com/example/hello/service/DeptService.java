package com.example.hello.service;

import com.example.hello.pojo.Dept;
import java.util.List;

public interface DeptService {
    List<Dept> list();
    
    void add(Dept dept);
    
    void update(Dept dept);
    
    void delete(Integer id);
    
    Dept getById(Integer id);
}
