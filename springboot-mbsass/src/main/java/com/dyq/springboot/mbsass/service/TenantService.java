package com.dyq.springboot.mbsass.service;

import com.dyq.springboot.mbsass.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * sass租户数据库信息(Tenant)表服务接口
 *
 * @author makejava
 * @since 2023-04-08 12:59:29
 */
public interface TenantService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Tenant queryById(Integer id);

    /**
     * 分页查询
     *
     * @param tenant      筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<Tenant> queryByPage(Tenant tenant, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param tenant 实例对象
     * @return 实例对象
     */
    Tenant insert(Tenant tenant);

    /**
     * 修改数据
     *
     * @param tenant 实例对象
     * @return 实例对象
     */
    Tenant update(Tenant tenant);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    Tenant getByName(String token);
}
