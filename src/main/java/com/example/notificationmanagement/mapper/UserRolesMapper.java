package com.example.notificationmanagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.notificationmanagement.pojo.UserRoles;
import com.example.notificationmanagement.pojo.Userpeimission;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.List;

/**
 * @description 针对表【user_roles】的数据库操作Mapper
 */
@Mapper
public interface UserRolesMapper extends BaseMapper<UserRoles> {

    @Select("SELECT RoleID FROM user_roles, users WHERE users.UserID = user_roles.UserID AND users.username = #{username}")
    Integer getRole(@Param("username") String username);

    @Delete("DELETE FROM user_roles WHERE UserID = #{userid}")
    int deleteById(Serializable userid);

    @Update("UPDATE user_roles SET roleid = #{roleid} WHERE userid = #{userid}")
    void updaterole(@Param("userid") Integer userid, @Param("roleid")Integer roleid);


    // 超级管理员 max 获取除自己外所有用户及其角色
    @Select("SELECT user_roles.UserID AS userid, users.username, roles.rolename " +
            "FROM user_roles " +
            "JOIN users ON users.UserID = user_roles.UserID " +
            "JOIN roles ON roles.RoleID = user_roles.RoleID " +
            "WHERE roles.rolename !='超级管理员max'")
    List<Userpeimission> getAllUsersExceptMax();

    // 超级管理员获取除超级管理员 max 及其他超级管理员外的所有用户及其角色
    @Select("SELECT user_roles.UserID AS userid, users.username, roles.rolename " +
            "FROM user_roles " +
            "JOIN users ON users.UserID = user_roles.UserID " +
            "JOIN roles ON roles.RoleID = user_roles.RoleID " +
            "WHERE roles.rolename !='超级管理员max' AND roles.rolename !='超级管理员'")
    List<Userpeimission> getAllUsersExceptMaxAndSuperAdmin();

    // 组织管理员查询指定组织内的用户及其角色
    @Select("SELECT user_roles.UserID AS userid, users.username, roles.rolename " +
            "FROM user_roles " +
            "JOIN users ON users.UserID = user_roles.UserID " +
            "JOIN roles ON roles.RoleID = user_roles.RoleID " +
            "JOIN user_organization ON user_organization.UserID = users.UserID " +
            "WHERE user_organization.OrganizationID = #{organizationId}")
    List<Userpeimission> getOrganizationUsersWithRoles(@Param("organizationId") int organizationId);
}
