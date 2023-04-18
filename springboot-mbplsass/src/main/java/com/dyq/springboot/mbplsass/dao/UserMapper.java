package com.dyq.springboot.mbplsass.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyq.springboot.mbplsass.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2023-04-07 16:50:38
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 自定义SQL：默认也会增加多租户条件
     * 参考打印的SQL
     * @return
     */
    Integer myCount();

    List<User> getUserAndAddr(@Param("username") String username);

    List<User> getAddrAndUser(@Param("name") String name);
}

