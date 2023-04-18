package com.dyq.springboot.mbplsass.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 多租户的配置属性
 *
 * @company
 *
 */
@Data
@Component
@ConfigurationProperties(prefix = "system.saas")
public class SaaSConfig {

	/**
	 * 多租户字段名
	 */
	private String tenantId = "tenant_id";
	
	/**
	 * 忽略多租户的表名
	 * <pre>
	 * 数据库中物理表表名
	 * </pre>
	 */
	private List<String> ignoreTables = new ArrayList<>();
	
}

