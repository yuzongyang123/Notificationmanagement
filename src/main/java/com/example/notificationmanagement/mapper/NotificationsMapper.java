package com.example.notificationmanagement.mapper;

import com.example.notificationmanagement.pojo.Notifications;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.notificationmanagement.pojo.NotifocationOrganization;
import com.example.notificationmanagement.pojo.Organization;
import com.example.notificationmanagement.pojo.Organizations;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Yuzongyang
* @description 针对表【notifications】的数据库操作Mapper
* @createDate 2024-05-28 12:34:17
* @Entity com.example.notificationmanagement.pojo.Notifications
*/
@Mapper
public interface NotificationsMapper extends BaseMapper<Notifications> {
    //获取用户有权限发通知的组织
    @Select
            ("SELECT o.OrganizationName,o.OrganizationID as organizationsid " +
            "FROM organizations o " +
            "JOIN user_organization uo ON o.OrganizationID = uo.OrganizationID " +
            "JOIN users u ON u.UserID = uo.UserID " +
            "WHERE u.username = #{username} AND uo.IsNotifier = 1")
    List<Organization> getNotifierOrganizations(String username);

    @Select("SELECT organizationId FROM user_organization WHERE userId = #{userId}")
    List<Integer> findOrganizationIdsByUserId(Integer userId);

    @Select("<script>" +
            "SELECT DISTINCT n.*, o.organizationname AS organizationName ,u.username FROM notifications n " +
            "JOIN user_organization uo ON n.organizationid = uo.organizationid " +
            "JOIN organizations o ON o.organizationid = uo.organizationid "+
            "JOIN users u ON u.userid = n.CreatedBy "+
            "WHERE n.organizationId IN " +
            "<foreach item='id' collection='organizationIds' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "ORDER BY n.createdat DESC" +
            "</script>")
    List<NotifocationOrganization> findNotificationsByOrganizationIds(@Param("organizationIds") List<Integer> organizationIds);

}




