package com.dyq.springboot.quart.job;


import com.dyq.springboot.quart.utils.QuartzreflectUtil;
import com.dyq.springboot.quart.utils.SpringContextUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * 定时任务需要实现的接口
 * @param <T>
 */
public interface DynamicJob<T> extends Job
{
	/**
	 * 需要执行任务的方法体
	 * @param args	可变参数，前端传递
	 * @return	object
	 */
	T task(Object... args);

	@Override
	default void execute(JobExecutionContext context) {
		QuartzreflectUtil quartzreflectUtil = SpringContextUtil.getBean(QuartzreflectUtil.class);
		quartzreflectUtil.reflect(context);
	}
}

