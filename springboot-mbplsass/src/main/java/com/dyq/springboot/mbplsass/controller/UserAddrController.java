package com.dyq.springboot.mbplsass.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dyq.springboot.mbplsass.entity.UserAddr;
import com.dyq.springboot.mbplsass.service.UserAddrService;
import com.dyq.springboot.mbplsass.util.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (UserAddr)表控制层
 *
 * @author makejava
 * @since 2023-04-07 16:11:03
 */
@RestController
@RequestMapping("userAddr")
public class UserAddrController {

    /**
     * 服务对象
     */
    @Resource
    private UserAddrService userAddrService;

    /**
     * 分页查询所有数据
     */
    @GetMapping
    public R page(@RequestParam int current, @RequestParam int size) {
        Page<UserAddr> page = new Page<>(current, size);
        return R.ok().setData(this.userAddrService.page(page));
    }


    /**
     * 通过主键查询单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return R.ok().setData(this.userAddrService.getById(id));
    }

    /**
     * 新增数据
     */
    @PostMapping
    public R save(@RequestBody UserAddr userAddr) {
        return R.ok().setData(this.userAddrService.save(userAddr));
    }

    /**
     * 修改数据
     */
    @PutMapping
    public R updateById(@RequestBody UserAddr userAddr) {
        return R.ok().setData(this.userAddrService.updateById(userAddr));
    }

    /**
     * 单条/批量删除数据
     */
    @DeleteMapping
    public R delete(@RequestParam List<Long> id) {
        return R.ok().setData(this.userAddrService.removeByIds(id));
    }
}

