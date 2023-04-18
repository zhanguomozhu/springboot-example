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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
//数据源primary-testdb库接口存放目录
@MapperScan(basePackages = "com.dyq.springboot.mds.dao.master",
            sqlSessionTemplateRef = "primarySqlSessionTemplate")
public class PrimaryDataSourceConfig {

  @Bean(name = "primaryDataSource")
  @ConfigurationProperties(prefix = "primarydb")   //数据源primary配置
  @Primary
  public DataSource primaryDataSource() {
    return new AtomikosDataSourceBean();
  }

  @Bean(name = "primarySqlSessionFactory")
  @Primary
  public SqlSessionFactory primarySqlSessionFactory(
          @Qualifier("primaryDataSource") DataSource dataSource)
          throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(dataSource);
    //设置XML文件存放位置
    bean.setMapperLocations(new PathMatchingResourcePatternResolver()
            .getResources("classpath:mapper/master/*.xml")); //注意这里testdb目录
    return bean.getObject();
  }

  @Bean(name = "primarySqlSessionTemplate")
  @Primary
  public SqlSessionTemplate primarySqlSessionTemplate(
          @Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory)
          throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory);
  }

}