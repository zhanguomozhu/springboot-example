package com.dyq.springboot.mbplsass.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dyq.springboot.mbplsass.entity.User;
import com.dyq.springboot.mbplsass.service.UserService;
import com.dyq.springboot.mbplsass.util.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2023-04-07 16:09:47
 */
@RestController
@RequestMapping("user")
public class UserController {

    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 分页查询所有数据
     */
    @GetMapping
    public R page(@RequestParam int current, @RequestParam int size) {
        Page<User> page = new Page<>(current, size);
        return R.ok().setData(this.userService.page(page));
    }


    /**
     * 通过主键查询单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return R.ok().setData(this.userService.getById(id));
    }

    /**
     * 新增数据
     */
    @PostMapping
    public R save(@RequestBody User user) {
        return R.ok().setData(this.userService.save(user));
    }

    /**
     * 修改数据
     */
    @PutMapping
    public R updateById(@RequestBody User user) {
        return R.ok().setData(this.userService.updateById(user));
    }

    /**
     * 单条/批量删除数据
     */
    @DeleteMapping
    public R delete(@RequestParam List<Long> id) {
        return R.ok().setData(this.userService.removeByIds(id));
    }
}

