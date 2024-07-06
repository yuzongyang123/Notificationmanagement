package com.example.notificationmanagement.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName roles
 */
@TableName(value ="roles")
@Data
public class Roles implements Serializable {
    /**
     * 
     */
    @TableId(value = "RoleID", type = IdType.AUTO)
    private Integer roleid;

    /**
     * 
     */
    @TableField(value = "RoleName")
    private String rolename;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}