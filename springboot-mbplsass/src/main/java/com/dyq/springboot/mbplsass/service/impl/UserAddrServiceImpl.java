package com.dyq.springboot.mbplsass.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyq.springboot.mbplsass.entity.UserAddr;
import com.dyq.springboot.mbplsass.dao.UserAddrMapper;
import com.dyq.springboot.mbplsass.service.UserAddrService;
import org.springframework.stereotype.Service;

/**
 * (UserAddr)表服务实现类
 *
 * @author makejava
 * @since 2023-04-07 16:11:03
 */
@Service("userAddrService")
public class UserAddrServiceImpl extends ServiceImpl<UserAddrMapper, UserAddr> implements UserAddrService {
}

