package com.dyq.springboot.mbpl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyq.springboot.mbpl.domain.User;
import com.dyq.springboot.mbpl.mapper.UserMapper;
import com.dyq.springboot.mbpl.service.UserService;
import org.springframework.stereotype.Service;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2023-04-07 15:27:11
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}

