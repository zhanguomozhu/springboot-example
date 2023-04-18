package com.dyq.springboot.jdbc.config;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


/**
 * JDBC多数据源配置
 */
//@Configuration
public class DataSourceConfigMd {

    @Bean(name = "primaryDataSource")
    @ConfigurationProperties(prefix="spring.datasource.primary")   //db1
    @Primary
    public DataSource primaryDataSource() {
            return DataSourceBuilder.create().build();
    }

    @Bean(name = "secondaryDataSource")
    @ConfigurationProperties(prefix="spring.datasource.secondary")   //db2
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="primaryJdbcTemplate")
    @Primary
    public JdbcTemplate primaryJdbcTemplate (@Qualifier("primaryDataSource") DataSource dataSource ) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name="secondaryJdbcTemplate")
    public JdbcTemplate secondaryJdbcTemplate(@Qualifier("secondaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}