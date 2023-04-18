package com.dyq.springboot.mds.confg;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = "com.dyq.springboot.mds.dao.slave",     //注意这里testdb2目录
            sqlSessionTemplateRef = "secondarySqlSessionTemplate")
public class SecondaryDataSourceConfig {

    @Bean(name = "secondaryDataSource")
    @ConfigurationProperties(prefix = "secondarydb")    //注意这里secondary配置
    public DataSource secondaryDataSource() {
        return new AtomikosDataSourceBean();
    }

    @Bean(name = "secondarySqlSessionFactory")
    public SqlSessionFactory secondarySqlSessionFactory(
                        @Qualifier("secondaryDataSource") DataSource dataSource)
                        throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //设置XML文件存放位置
        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/slave/*.xml")); //注意这里testdb2目录
        return bean.getObject();
    }

    @Bean(name = "secondarySqlSessionTemplate")
    public SqlSessionTemplate secondarySqlSessionTemplate(
                        @Qualifier("secondarySqlSessionFactory") SqlSessionFactory sqlSessionFactory)
                        throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}