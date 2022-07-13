package com.practice.shardingsphere.model.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;
import static com.baomidou.mybatisplus.annotation.IdType.INPUT;

/**
 * 
 * @TableName bs_message
 */
@TableName(value ="bs_message")
@Data
public class BsMessage implements Serializable {
    /**
     * 主键
     */
    @TableId
    private Long msgId;

    /**
     * 标题
     */
    private String title;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 租户号
     */
    private String tenantId;

    /**
     * 乐观锁
     */
    private String revision;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private Date updatedTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}