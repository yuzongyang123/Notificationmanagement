package com.example.notificationmanagement.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName group_messages
 */
@TableName(value ="group_messages")
@Data
public class GroupMessages implements Serializable {
    /**
     * 
     */
    @TableId(value = "GroupMessageID", type = IdType.AUTO)
    private Integer groupmessageid;

    /**
     * 
     */
    @TableField(value = "SenderID")
    private Integer senderid;

    /**
     * 
     */
    @TableField(value = "GroupID")
    private Integer groupid;

    /**
     * 
     */
    @TableField(value = "Content")
    private String content;

    /**
     * 
     */
    @TableField(value = "SentAt")
    private Date sentat;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}