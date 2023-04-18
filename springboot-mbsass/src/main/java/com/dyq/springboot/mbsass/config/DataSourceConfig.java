package com.dyq.springboot.mbsass.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义动态数据源配置类
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;

    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;

    @Value("${mybatis.configuration.map-underscore-to-camel-case}")
    private Boolean mapUnderscoreToCamelCase;


    /**
     * 默认基础数据源
     *
     * @return
     */
    @Bean("defaultSource")
    @ConfigurationProperties("spring.datasource")
    public DataSource defaultSource() {
        return DataSourceBuilder.create().build();
    }


    /**
     * 自定义动态数据源
     *
     * @return
     */
    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("default", defaultSource());
        // 默认数据源
        dynamicDataSource.setDefaultDataSource(defaultSource());
        // 动态数据源
        dynamicDataSource.setDataSources(dataSourceMap);
        return dynamicDataSource;
    }

    /**
     * @return 修改Mybatis数据源配置
     * @throws IOException
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        // 配置自定义动态数据源
        sessionFactory.setDataSource(dynamicDataSource());
        // 开启驼峰转下划线设置
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);
        sessionFactory.setConfiguration(configuration);
        // 实体、Mapper类映射
        sessionFactory.setTypeAliasesPackage(typeAliasesPackage);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        return sessionFactory;
    }


    /**
     * 开启动态数据源@Transactional注解事务管理的支持
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}
