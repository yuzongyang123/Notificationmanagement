package com.example.notificationmanagement.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName users
 */
@TableName(value ="users")
@Data
public class Users implements Serializable {
    /**
     * 
     */
    @TableId(value = "UserID", type = IdType.AUTO)
    private Integer userid;

    /**
     * 
     */
    @TableField(value = "Username")
    private String username;

    /**
     * 
     */
    @TableField(value = "Password")
    private String password;


    /**
     * 
     */
    @TableField(value = "IsActive")
    private Boolean isactive;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}