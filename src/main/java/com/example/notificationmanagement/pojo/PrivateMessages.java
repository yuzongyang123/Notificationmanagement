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
 * @TableName private_messages
 */
@TableName(value ="private_messages")
@Data
public class PrivateMessages implements Serializable {
    /**
     * 
     */
    @TableId(value = "MessageID", type = IdType.AUTO)
    private Integer messageid;

    /**
     * 
     */
    @TableField(value = "SenderID")
    private Integer senderid;

    /**
     * 
     */
    @TableField(value = "ReceiverID")
    private Integer receiverid;

    /**
     * 
     */
    @TableField(value = "Content")
    private String content;

    /**
     * 
     */
    @TableField(value = "ImageURL")
    private String imageurl;

    /**
     * 
     */
    @TableField(value = "SentAt")
    private Date sentat;

    /**
     * 
     */
    @TableField(value = "IsRead")
    private Integer isread;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}