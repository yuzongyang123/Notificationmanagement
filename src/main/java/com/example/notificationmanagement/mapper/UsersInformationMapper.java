package com.example.notificationmanagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.notificationmanagement.pojo.UsersInformation;
import com.example.notificationmanagement.pojo.UsersInformationAndactive;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.List;

/**
 * 针对表【users_information】的数据库操作Mapper
 */
@Mapper
public interface UsersInformationMapper extends BaseMapper<UsersInformation> {

    //获取普通用户
    @Select("SELECT distinct ui.userid, ui.email, ui.phone, ui.address, u.username, u.IsActive " +
            "FROM user_roles ur " +
            "JOIN users_information ui ON ur.userid = ui.userid " +
            "JOIN users u ON ur.userid = u.userid " +
            "WHERE ur.roleid != 1 AND ur.roleid != 2")
    List<UsersInformationAndactive> selectallusers();

    // 重声明 deleteById 方法
    @Delete("DELETE FROM users_information WHERE userid = #{userid}")
    int deleteById(Serializable userid);

    // 自定义重置密码方法
    @Update("UPDATE users SET password = #{password} WHERE userid = #{userid}")
    void resetPassword(@Param("userid") int userid, @Param("password") String password);


    @Select("select email,phone,address,username from users_information where username=#{username}")
    UsersInformation selectuser(String username);




}




