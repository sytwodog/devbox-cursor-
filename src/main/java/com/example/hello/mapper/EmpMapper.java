package com.example.hello.mapper;

import com.example.hello.pojo.Emp;
import org.apache.ibatis.annotations.*;

import java.util.List;

import java.util.List;

@Mapper
public interface EmpMapper {
    
    @Update("UPDATE emp SET password = #{password} WHERE id = #{id}")
    void updatePassword(Emp emp);
    
    @Select("""
        SELECT e.*, d.name as dept_name 
        FROM emp e 
        LEFT JOIN dept d ON e.dept_id = d.id 
        WHERE e.is_deleted = 0 
        ${conditions}
        ORDER BY e.update_time DESC
    """)
    List<Emp> list(@Param("conditions") String conditions);

    @Select("SELECT * FROM emp WHERE id = #{id} AND is_deleted = 0")
    Emp getById(Integer id);

    @Select("SELECT COUNT(*) FROM emp WHERE username = #{username} AND is_deleted = 0")
    int countByUsername(String username);

    @Select("SELECT COUNT(*) FROM emp WHERE phone = #{phone} AND is_deleted = 0")
    int countByPhone(String phone);

    @Insert("""
        INSERT INTO emp(username, name, gender, phone, position, salary, image, 
        entry_date, dept_id) 
        VALUES(#{username}, #{name}, #{gender}, #{phone}, #{position}, #{salary}, 
        #{image}, #{entryDate}, #{deptId})
    """)
    void insert(Emp emp);

    @Update("""
        UPDATE emp SET username = #{username}, name = #{name}, gender = #{gender}, 
        phone = #{phone}, position = #{position}, salary = #{salary}, 
        image = #{image}, entry_date = #{entryDate}, dept_id = #{deptId} 
        WHERE id = #{id}
    """)
    void update(Emp emp);

    @Update("UPDATE emp SET is_deleted = 1 WHERE id = #{id}")
    void deleteById(Integer id);
    
    @Update("""
        <script>
        UPDATE emp SET is_deleted = 1 
        WHERE id IN 
        <foreach collection="ids" item="id" open="(" separator="," close=")">
            #{id}
        </foreach>
        </script>
    """)
    void deleteBatch(@Param("ids") List<Integer> ids);
    
    @Select("SELECT * FROM emp WHERE username = #{username} AND is_deleted = 0")
    Emp getByUsername(String username);
}
