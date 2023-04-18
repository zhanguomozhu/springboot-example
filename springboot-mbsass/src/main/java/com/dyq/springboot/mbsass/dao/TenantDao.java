package com.dyq.springboot.mbsass.dao;

import com.dyq.springboot.mbsass.entity.Tenant;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * sass租户数据库信息(Tenant)表数据库访问层
 *
 * @author makejava
 * @since 2023-04-08 12:59:27
 */
public interface TenantDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Tenant queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param tenant   查询条件
     * @param pageable 分页对象
     * @return 对象列表
     */
    List<Tenant> queryAllByLimit(Tenant tenant, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param tenant 查询条件
     * @return 总行数
     */
    long count(Tenant tenant);

    /**
     * 新增数据
     *
     * @param tenant 实例对象
     * @return 影响行数
     */
    int insert(Tenant tenant);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Tenant> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Tenant> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Tenant> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Tenant> entities);

    /**
     * 修改数据
     *
     * @param tenant 实例对象
     * @return 影响行数
     */
    int update(Tenant tenant);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    Tenant getByName(String name);
}

