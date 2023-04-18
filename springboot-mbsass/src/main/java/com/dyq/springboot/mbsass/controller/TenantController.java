package com.dyq.springboot.mbsass.controller;

import com.dyq.springboot.mbsass.entity.Tenant;
import com.dyq.springboot.mbsass.service.TenantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * sass租户数据库信息(Tenant)表控制层
 *
 * @author makejava
 * @since 2023-04-08 12:59:26
 */
@RestController
@RequestMapping("tenant")
public class TenantController {
    /**
     * 服务对象
     */
    @Resource
    private TenantService tenantService;

    /**
     * 分页查询
     *
     * @param tenant      筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Tenant>> queryByPage(Tenant tenant, PageRequest pageRequest) {
        return ResponseEntity.ok(this.tenantService.queryByPage(tenant, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Tenant> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.tenantService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param tenant 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Tenant> add(Tenant tenant) {
        return ResponseEntity.ok(this.tenantService.insert(tenant));
    }

    /**
     * 编辑数据
     *
     * @param tenant 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Tenant> edit(Tenant tenant) {
        return ResponseEntity.ok(this.tenantService.update(tenant));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.tenantService.deleteById(id));
    }

}

