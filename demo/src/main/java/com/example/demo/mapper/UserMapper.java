package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user")
    List<User> findAll();
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(Long id);
    @Insert("INSERT INTO user(name, age, email) VALUES(#{name}, #{age}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Update("UPDATE user SET name=#{name}, age=#{age}, email=#{email} WHERE id=#{id}")
    int update(User user);

    @Delete("DELETE FROM user WHERE id=#{id}")
    int deleteById(Long id);

}
