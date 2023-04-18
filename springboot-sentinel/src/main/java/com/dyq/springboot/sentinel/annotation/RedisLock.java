package com.dyq.springboot.sentinel.annotation;

import java.lang.annotation.*;

/**
 * 用于标记redis锁
 * created at 2019-07-05 15:15
 * @author dyq
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {

	/**
	 * 可使用SpEL传方法参数
	 * @return
	 */
	String value() default "";

	/**
	 * redis锁的key值
	 * @return
	 */
	String lockKey() default "";
}
