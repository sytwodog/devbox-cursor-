package com.example.hello.mapper;

import com.example.hello.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {
    
    @Select("SELECT * FROM dept ORDER BY update_time DESC")
    List<Dept> list();

    @Select("SELECT * FROM dept WHERE id = #{id}")
    Dept getById(Integer id);

    @Select("SELECT COUNT(*) FROM dept WHERE name = #{name}")
    int countByName(String name);

    @Insert("INSERT INTO dept(name) VALUES(#{name})")
    void insert(Dept dept);

    @Update("UPDATE dept SET name = #{name} WHERE id = #{id}")
    void update(Dept dept);

    @Delete("DELETE FROM dept WHERE id = #{id}")
    void deleteById(Integer id);
}
