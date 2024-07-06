package com.example.notificationmanagement.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName organizations
 */
@TableName(value ="organizations")
@Data
public class Organizations implements Serializable {
    /**
     * 
     */
    @TableId(value = "OrganizationID", type = IdType.AUTO)
    private Integer organizationid;

    /**
     * 
     */
    @TableField(value = "OrganizationName")
    private String organizationname;

    /**
     * 
     */
    @TableField(value = "CreatedBy")
    private Integer createdby;

    /**
     * 
     */
    @TableField(value = "IsActive")
    private Integer isactive;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private boolean IsNotifier;
}