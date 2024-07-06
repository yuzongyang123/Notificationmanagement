package com.example.notificationmanagement.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user_roles
 */
@TableName(value ="user_roles")
@Data
public class UserRoles implements Serializable {
    /**
     * 
     */
    @TableId(value = "UserRoleID", type = IdType.AUTO)
    private Integer userroleid;

    /**
     * 
     */
    @TableField(value = "UserID")
    private Integer userid;

    /**
     * 
     */
    @TableField(value = "RoleID")
    private Integer roleid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}