package com.dyq.springboot.activiti.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyq.springboot.activiti.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.dyq.springboot.activiti.entity.Demand;

/**
 * (Demand)表数据库访问层
 *
 * @author makejava
 * @since 2023-04-17 12:09:46
 */
@Mapper
public interface DemandDao extends BaseMapper<Demand> {

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Demand> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Demand> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Demand> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Demand> entities);

    //获取开发组长
    List<User> getKaiFaZuZhangList();

    //获取开发经理
    List<User> getKaiFaJingLiList();

    //获取项目经理
    List<User> getProjectJingLiList();

    void deletevarinst(String id);

}

