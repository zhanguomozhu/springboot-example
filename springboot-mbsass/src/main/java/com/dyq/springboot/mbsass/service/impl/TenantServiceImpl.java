package com.dyq.springboot.mbsass.service.impl;

import com.dyq.springboot.mbsass.entity.Tenant;
import com.dyq.springboot.mbsass.dao.TenantDao;
import com.dyq.springboot.mbsass.service.TenantService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * sass租户数据库信息(Tenant)表服务实现类
 *
 * @author makejava
 * @since 2023-04-08 12:59:29
 */
@Service("tenantService")
public class TenantServiceImpl implements TenantService {
    @Resource
    private TenantDao tenantDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Tenant queryById(Integer id) {
        return this.tenantDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param tenant      筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<Tenant> queryByPage(Tenant tenant, PageRequest pageRequest) {
        long total = this.tenantDao.count(tenant);
        return new PageImpl<>(this.tenantDao.queryAllByLimit(tenant, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param tenant 实例对象
     * @return 实例对象
     */
    @Override
    public Tenant insert(Tenant tenant) {
        this.tenantDao.insert(tenant);
        return tenant;
    }

    /**
     * 修改数据
     *
     * @param tenant 实例对象
     * @return 实例对象
     */
    @Override
    public Tenant update(Tenant tenant) {
        this.tenantDao.update(tenant);
        return this.queryById(tenant.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.tenantDao.deleteById(id) > 0;
    }

    @Override
    public Tenant getByName(String token) {
        return this.tenantDao.getByName(token);
    }
}
