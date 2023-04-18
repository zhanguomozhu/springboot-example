package com.dyq.springboot.mbplsass.service;

import com.dyq.springboot.mbplsass.dao.UserMapper;
import com.dyq.springboot.mbplsass.entity.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserMapper mapper;

    @Test
    public void aInsert() {
        User user = new User();
        user.setName("333");
        Assertions.assertTrue(mapper.insert(user) > 0);
        user = mapper.selectById(user.getId());
        Assertions.assertTrue(1 == user.getTenantId());
    }


    @Test
    public void bDelete() {
        Assertions.assertTrue(mapper.deleteById(3L) > 0);
    }


    @Test
    public void cUpdate() {
        User user = new User();
        user.setId(1L);
        user.setName("mp");
        Assertions.assertTrue(1 == mapper.updateById(user));
    }

    @Test
    public void dSelect() {
        List<User> userList = mapper.selectList(null);
        userList.forEach(u -> Assertions.assertTrue(1 == u.getTenantId()));
    }

    /**
     * 自定义SQL：默认也会增加多租户条件
     * 参考打印的SQL
     */
    @Test
    public void manualSqlTenantFilterTest() {
        System.out.println(mapper.myCount());
    }

    @Test
    public void testTenantFilter(){
//        mapper.getAddrAndUser(null).forEach(System.out::println);
//        mapper.getAddrAndUser("add").forEach(System.out::println);
//        mapper.getUserAndAddr(null).forEach(System.out::println);
        mapper.getUserAndAddr("J").forEach(System.out::println);
    }

}