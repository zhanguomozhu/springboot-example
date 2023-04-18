package com.dyq.springboot.mbsass.aspect;

import com.dyq.springboot.mbsass.config.DataSourceContextHolder;
import com.dyq.springboot.mbsass.config.DynamicDataSource;
import com.dyq.springboot.mbsass.entity.Tenant;
import com.dyq.springboot.mbsass.service.TenantService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

@Aspect
@Order(1)  //该切面应当先于 @Transactional 执行
@Component
public class DynamicDataSourceAspect {

    private Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Autowired
    private TenantService tenantService;
    @Autowired
    private DynamicDataSource dynamicDataSource;

    /**
     * 切换数据源
     */
    @Before("execution(public * com.dyq.springboot.mbsass.controller.*.*(..))")
    public void switchDataSource() {
        logger.info("sass切换数据源");
        // 获取token
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("token");
        // 将新租户的数据源添加到动态数据源
        if (!DataSourceContextHolder.containDataSourceKey(token)) {
            Tenant tenant = tenantService.getByName(token);
            logger.info("sass当前租户信息：{}", tenant.toString());
            if (tenant == null) throw new RuntimeException("租户信息异常");
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.driverClassName(tenant.getDriverClassName());
            dataSourceBuilder.url(tenant.getJdbcUrl());
            dataSourceBuilder.username(tenant.getUsername());
            dataSourceBuilder.password(tenant.getPassword());
            DataSource source = dataSourceBuilder.build();
            dynamicDataSource.addDataSource(token, source);
        }
        // 切换数据源
        DataSourceContextHolder.setDataSourceKey(token);
    }

    /**
     * 重置数据源
     */
    @After("execution(public * com.dyq.springboot.mbsass.controller.*.*(..))")
    public void restoreDataSource() {
        // 将数据源置为默认数据源
        logger.info("sass重置数据源");
        DataSourceContextHolder.clearDataSourceKey();
    }
}
