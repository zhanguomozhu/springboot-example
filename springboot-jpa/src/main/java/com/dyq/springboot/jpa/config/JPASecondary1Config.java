package com.dyq.springboot.jpa.config;

import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

//非分布式事务多数据源

//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        entityManagerFactoryRef="entityManagerFactorySecondary",
//        transactionManagerRef="transactionManagerSecondary",
//        basePackages= {"com.dyq.springboot.jpa.repository.db2"}) //换成你自己的Repository所在位置
//public class JPASecondary1Config {
//
//    @Resource
//    private JpaProperties jpaProperties;
//
//    @Resource
//    private HibernateProperties hibernateProperties;
//
//
//    @Bean(name = "secondaryDataSource")
//    @ConfigurationProperties(prefix="spring.datasource.secondary")   //使用application.yml的secondary数据源配置
//    public DataSource secondaryDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "entityManagerSecondary")      //secondary实体管理器
//    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
//        return entityManagerFactorySecondary(builder).getObject().createEntityManager();
//
//    }
//
//
//
//    @Bean(name = "entityManagerFactorySecondary")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactorySecondary (EntityManagerFactoryBuilder builder) {
//
//        Map<String,Object> properties =
//                hibernateProperties.determineHibernateProperties(
//                        jpaProperties.getProperties(),
//                        new HibernateSettings());
//
//        return builder
//                .dataSource(secondaryDataSource())
//                .properties(properties)
//                .packages("com.dyq.springboot.jpa.entity") //换成你自己的实体类所在位置
//                .persistenceUnit("secondaryPersistenceUnit")
//                .build();
//    }
//
//
//    @Bean(name = "transactionManagerSecondary")
//    PlatformTransactionManager transactionManagerSecondary(EntityManagerFactoryBuilder builder) {
//        return new JpaTransactionManager(entityManagerFactorySecondary(builder).getObject());
//
//    }
//
//}