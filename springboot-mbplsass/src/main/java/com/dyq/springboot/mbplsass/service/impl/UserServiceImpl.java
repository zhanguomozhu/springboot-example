package com.dyq.springboot.mbplsass.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyq.springboot.mbplsass.entity.User;
import com.dyq.springboot.mbplsass.dao.UserMapper;
import com.dyq.springboot.mbplsass.service.UserService;
import org.springframework.stereotype.Service;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2023-04-07 16:09:47
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}

