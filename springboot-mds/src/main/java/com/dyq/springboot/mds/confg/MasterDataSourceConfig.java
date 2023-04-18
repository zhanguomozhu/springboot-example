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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

//@Configuration
//@MapperScan(basePackages = MasterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "masterSqlSessionFactory")
//public class MasterDataSourceConfig {
//
//    static final String PACKAGE = "com.dyq.springboot.mds.dao.master";
//    static final String MAPPER_LOCATION = "classpath:mapper/master/*.xml";
//
//    @Value("${master.datasource.driver-class-name}")
//    private String driverClassName;
//
//    @Value("${master.datasource.url}")
//    private String url;
//
//    @Value("${master.datasource.username}")
//    private String username;
//
//    @Value("${master.datasource.password}")
//    private String password;
//
//    @Bean(name = "masterDataSource")
//    @Primary
//    public DataSource dataSource() {
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName(this.driverClassName);
//        dataSource.setUrl(this.url);
//        dataSource.setUsername(this.username);
//        dataSource.setPassword(this.password);
//        return dataSource;
//    }
//
//    @Bean(name = "masterSqlSessionFactory")
//    @Primary
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setMapperLocations(
//                new PathMatchingResourcePatternResolver().getResources(MasterDataSourceConfig.MAPPER_LOCATION));
//        return bean.getObject();
//    }
//
//    @Bean(name = "masterTransactionManager")
//    @Primary
//    public DataSourceTransactionManager transactionManager(@Qualifier("masterDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name = "masterSqlSessionTemplate")
//    @Primary
//    public SqlSessionTemplate testSqlSessionTemplate(
//            @Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//}