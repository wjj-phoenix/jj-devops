package com.phoenix.devops.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordLogInfo {

	/**
	 * 平台
	 */
	private String platform;

	/**
	 * 租户
	 */
	private String tenant;

	/**
	 * 操作者
	 */
	private String operator;

	/**
	 * 业务id
	 */
	private String bizNo;

	/**
	 * 模块
	 */
	private String module;

	/**
	 * 操作类型
	 */
	private String type;

	/**
	 * 成功操作内容
	 */
	private String content;

	/**
	 * 操作时间 时间戳单位：ms
	 */
	private Long operateTime;

	/**
	 * 操作花费的时间 单位：ms
	 */
	private Long executeTime;

	/**
	 * 是否调用成功
	 */
	private Boolean success;

	/**
	 * 执行后返回的json字符串
	 */
	private String result;

	private String errorMsg;

	/**
	 * 详细
	 */
	private String details;

	/**
	 * 详细的字段变更
	 */
	private List<FieldInfo> compareFields;


}
