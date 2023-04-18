package com.dyq.springboot.sentinel.aspect;

import com.dyq.springboot.sentinel.annotation.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * redis分布式锁的切面
 * created at 2019-07-31 11:05
 * @author dyq
 */
@Aspect
@Component
@Slf4j
public class RedisLockAspect {

	private final static int DEAL_ACTION_LOCK_WAITING_TIME=10;

	@Autowired
	private RedisLockRegistry redisLockRegistry;


	@Around(value = "@annotation(redisLock)")
	public Object redisLock(ProceedingJoinPoint joinPoint,
			RedisLock redisLock) {

		Object output = null;
		try {

			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Method method = signature.getMethod();
			Object[] arguments = joinPoint.getArgs();

			// 获取锁的key
			Object lockKey = parseSpel(redisLock.lockKey(), method, arguments);
			Lock lock = redisLockRegistry.obtain(lockKey);

			try {
				boolean ifLock = lock.tryLock(DEAL_ACTION_LOCK_WAITING_TIME, TimeUnit.SECONDS);
				log.info("线程[{}]是否获取到了锁：{}", Thread.currentThread().getName(), ifLock);

				/*
				 * 可以获取到锁，说明当前没有线程在执行该方法
				 */
				if (ifLock) {
					// 具体执行
					output = joinPoint.proceed();
				}
				else {
					arguments[1] = "已有任务正在执行，请勿重复请求";
					output = joinPoint.proceed(arguments);
					log.info("线程[{}]未获取到锁，目前锁详情信息为：{}", Thread.currentThread().getName(), lock);
				}
			}
			catch (Exception e) {
				log.error("执行核心奖励扫描时出错:{}", e.getMessage());
			}
			finally {
				log.info("尝试解锁[{}]", lockKey);
				try {
					lock.unlock();
					log.info("[{}]解锁成功", lockKey);
				}
				catch (Exception e) {
					log.error("解锁dealAction出错:{}", e.getMessage());
				}
			}
		}
		catch (Throwable e) {
			log.error("aop redis distributed lock error:{}", e.getLocalizedMessage());
		}
		return output;
	}

	/**
	 * 解析SpEL表达式
	 * @param key SpEL表达式
	 * @param method 反射得到的方法
	 * @param args 反射得到的方法参数
	 * @return 解析后SpEL表达式对应的值
	 */
	public static Object parseSpel(String key, Method method, Object[] args) {
		// 创建解析器
		ExpressionParser parser = new SpelExpressionParser();
		// 通过Spring的LocalVariableTableParameterNameDiscoverer获取方法参数名列表
		LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
		String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
		// 构造上下文
		EvaluationContext context = new StandardEvaluationContext();
		if (args.length == parameterNames.length) {
			for (int i = 0, len = args.length; i < len; i++) {
				// 使用setVariable方法来注册自定义变量
				context.setVariable(parameterNames[i], args[i]);
			}
		}
		return parser.parseExpression(key).getValue(context);
	}


}
