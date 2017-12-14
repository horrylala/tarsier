package com.sf.tarsier.mvc.system.entity;

/**
 * 日志分文件保存
 * 异常日志专门存一个文件
 */
public final class LoggerType {
	private LoggerType(){}

	public static final String COMMON = "common";
	
	public static final String FILTER = "filter";
	
	public static final String TASK = "task";
	
	public static final String TEST = "test";
}
