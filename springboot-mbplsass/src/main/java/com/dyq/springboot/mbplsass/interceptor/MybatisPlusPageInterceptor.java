package com.dyq.springboot.mbplsass.interceptor;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Plus 3.5.1 拦截器
 *
 * @author makejava
 * @since 2023-04-07 16:09:48
 */
@Configuration
public class MybatisPlusPageInterceptor {
    /**
     * 新版分页插件设置
     */
    @Bean
    public MybatisPlusInterceptor getPaginationInnerInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}

