package com.dyq.springboot.mbplsass.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Configuration
@MapperScan("com.dyq.springboot.mbplsass.dao")
public class MybatisPlusConfig {

    @Autowired
    private SaaSConfig saaSConfig;
    /**
     * 新多租户插件配置,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存万一出现问题
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加租户
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            // todo 获取当前用户租户ID
            Long tenantId = 1L;

            /**
             * 设置租户ID的值
             * @return
             */
            @Override
            public Expression getTenantId() {
                return new LongValue(tenantId);
            }

            // 这是 default 方法,默认返回 tenant_id 表示租户ID字段名称,租户字段为tenant_id时,不用重写此方法
            @Override
            public String getTenantIdColumn() {
                return "tenant_id";
            }

            // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
            @Override
            public boolean ignoreTable(String tableName) {
                // 超级管理员端的表可以进行忽略(如 租户管理表等)
                List<String> ignoreTables = saaSConfig.getIgnoreTables();
                long count = ignoreTables.stream().filter(e -> e.equalsIgnoreCase(tableName)).count();
                return count > 0;
                //return !"user".equalsIgnoreCase(tableName);
            }
        }));
        // 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
        // 用了分页插件必须设置 MybatisConfiguration#useDeprecatedExecutor = false
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

//    @Bean
//    public ConfigurationCustomizer configurationCustomizer() {
//        return configuration -> configuration.setUseDeprecatedExecutor(false);
//    }
}

