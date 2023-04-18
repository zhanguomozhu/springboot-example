package com.dyq.springboot.mds.confg;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

//@Configuration
//@MapperScan(basePackages = SlaveDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "slaveSqlSessionFactory")
//public class SlaveDataSourceConfig {
//
//    static final String PACKAGE = "com.dyq.springboot.mds.dao.slave";
//    static final String MAPPER_LOCATION = "classpath:mapper/slave/*.xml";
//
//    @Value("${slave.datasource.driver-class-name}")
//    private String driverClassName;
//
//    @Value("${slave.datasource.url}")
//    private String url;
//
//    @Value("${slave.datasource.username}")
//    private String username;
//
//    @Value("${slave.datasource.password}")
//    private String password;
//
//    @Bean(name = "slaveDataSource")
//    public DataSource dataSource() {
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName(this.driverClassName);
//        dataSource.setUrl(this.url);
//        dataSource.setUsername(this.username);
//        dataSource.setPassword(this.password);
//        return dataSource;
//    }
//
//    @Bean(name = "slaveSqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("slaveDataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setMapperLocations(
//                new PathMatchingResourcePatternResolver().getResources(SlaveDataSourceConfig.MAPPER_LOCATION));
//        return bean.getObject();
//    }
//
//    @Bean(name = "slaveTransactionManager")
//    public DataSourceTransactionManager transactionManager(@Qualifier("slaveDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name = "slaveSqlSessionTemplate")
//    public SqlSessionTemplate testSqlSessionTemplate(
//            @Qualifier("slaveSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//}