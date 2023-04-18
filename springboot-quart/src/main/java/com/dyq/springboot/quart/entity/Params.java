package com.dyq.springboot.quart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Params {

	@NotNull(message = "任务名必填!")
	private String jobName;

	@NotNull(message = "分组名必填!")
	private String jobGroupName;

	@NotNull(message = "类名不能为空")
	private String className;

	@NotNull(message = "执行cron必填!")
	private String cron;

	private String method;

	private List<Map<String,Object>> paramData;

	private Object[] finalMethodParam;
}

