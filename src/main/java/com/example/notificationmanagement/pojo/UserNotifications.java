package com.example.notificationmanagement.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user_notifications
 */
@TableName(value ="user_notifications")
@Data
public class UserNotifications implements Serializable {
    /**
     * 
     */
    @TableId(value = "UserNotificationID", type = IdType.AUTO)
    private Integer usernotificationid;

    /**
     * 
     */
    @TableField(value = "UserID")
    private Integer userid;

    /**
     * 
     */
    @TableField(value = "NotificationID")
    private Integer notificationid;

    /**
     * 
     */
    @TableField(value = "IsRead")
    private Integer isread;

    /**
     * 
     */
    @TableField(value = "IsFavorite")
    private Integer isfavorite;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}