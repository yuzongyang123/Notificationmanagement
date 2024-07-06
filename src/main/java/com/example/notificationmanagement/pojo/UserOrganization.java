package com.example.notificationmanagement.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user_organization
 */
@TableName(value ="user_organization")
@Data
public class UserOrganization implements Serializable {
    /**
     * 
     */
    @TableId(value = "UserOrganizationID", type = IdType.AUTO)
    private Integer userorganizationid;

    /**
     * 
     */
    @TableField(value = "UserID")
    private Integer userid;

    /**
     * 
     */
    @TableField(value = "OrganizationID")
    private Integer organizationid;

    @TableField(value = "IsNotifier")
    private boolean IsNotifier;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}