package com.dyq.springboot.mbplsass.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (UserAddr)实体类
 *
 * @author makejava
 * @since 2023-04-07 16:50:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddr implements Serializable {
    private static final long serialVersionUID = 376068873550742695L;
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * user.id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 地址名称
     */
    @TableField(value = "name")
    private String name;
}

