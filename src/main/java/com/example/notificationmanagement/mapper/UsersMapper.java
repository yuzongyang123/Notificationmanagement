package com.example.notificationmanagement.mapper;

import com.example.notificationmanagement.pojo.UserOrganizationDto;
import com.example.notificationmanagement.pojo.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import com.example.notificationmanagement.pojo.Users;

import java.io.Serializable;
import java.util.List;

/**
* @author Yuzongyang
* @description 针对表【users】的数据库操作Mapper
* @createDate 2024-05-28 12:34:17
* @Entity com.example.notificationmanagement.pojo.Users
*/
@Mapper

public interface UsersMapper extends BaseMapper<Users> {
    @Insert("INSERT INTO users (username, password) VALUES (#{username}, #{password})")
    int registerUser(@Param("username") String username, @Param("password") String password);

    @Select("SELECT userid FROM users WHERE username = #{username}")
    Integer findIdByUsername(String username);

    @Delete("DELETE FROM users WHERE userid = #{userid}")
    int deleteById(Serializable userid);






}




