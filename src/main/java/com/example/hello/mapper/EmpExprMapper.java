package com.example.hello.mapper;

import com.example.hello.pojo.EmpExpr;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EmpExprMapper {
    
    @Select("SELECT * FROM emp_expr WHERE emp_id = #{empId} ORDER BY start_date DESC")
    List<EmpExpr> listByEmpId(Integer empId);

    @Select("SELECT * FROM emp_expr WHERE id = #{id}")
    EmpExpr getById(Integer id);

    @Insert("""
        INSERT INTO emp_expr(emp_id, start_date, end_date, company, position) 
        VALUES(#{empId}, #{startDate}, #{endDate}, #{company}, #{position})
    """)
    void insert(EmpExpr empExpr);

    @Update("""
        UPDATE emp_expr SET start_date = #{startDate}, end_date = #{endDate}, 
        company = #{company}, position = #{position} 
        WHERE id = #{id} AND emp_id = #{empId}
    """)
    void update(EmpExpr empExpr);

    @Delete("DELETE FROM emp_expr WHERE id = #{id} AND emp_id = #{empId}")
    void delete(@Param("empId") Integer empId, @Param("id") Integer id);
    
    @Delete("DELETE FROM emp_expr WHERE emp_id = #{empId}")
    void deleteByEmpId(Integer empId);
}
