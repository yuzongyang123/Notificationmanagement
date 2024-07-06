package com.example.notificationmanagement.mapper;

import com.example.notificationmanagement.pojo.Organizations;
import com.example.notificationmanagement.pojo.UserOrganization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.notificationmanagement.pojo.UserOrganizationDto;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.List;
@Mapper
/**
* @author Yuzongyang
* @description 针对表【user_organization】的数据库操作Mapper
* @createDate 2024-05-28 12:34:17
* @Entity com.example.notificationmanagement.pojo.UserOrganization
*/
public interface UserOrganizationMapper extends BaseMapper<UserOrganization> {

    @Select("SELECT u.userid, u.username, uo.isNotifier " +
            "FROM users u " +
            "JOIN user_organization uo ON u.userid = uo.userid " +
            "JOIN organizations o ON uo.organizationId = o.organizationId " +
            "WHERE uo.organizationId = #{organizationId} AND uo.userid != o.createdBy")
    List<UserOrganizationDto> selectMembersByOrganizationId(@Param("organizationId") Integer organizationId);

    @Delete("Delete  from user_organization where OrganizationID = #{OrganizationID}")
    int deleteuser_organization(Serializable OrganizationID);

    @Delete("DELETE FROM user_organization WHERE OrganizationID = #{OrganizationID} AND userid = #{userid}")
    int deletemember(@Param("OrganizationID") Serializable OrganizationID, @Param("userid") Serializable userid);

    @Insert("INSERT INTO user_organization(organizationId, userid) VALUES (#{organizationId}, #{userid})")
    int addmember(@Param("organizationId") Integer organizationId, @Param("userid") Integer userid);

    @Update("UPDATE user_organization set  IsNotifier = #{isNotifier} WHERE UserID = #{userid} and OrganizationID = #{organizationId}")
    int updateUserRole(@Param("organizationId") Integer organizationId, @Param("userid") Integer userid, @Param("isNotifier") Boolean isNotifier);
}




