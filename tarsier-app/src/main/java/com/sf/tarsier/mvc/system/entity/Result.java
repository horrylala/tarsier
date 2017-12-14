package com.sf.tarsier.mvc.system.entity;

import java.io.Serializable;

/**
 * 返回结果公共类
 * @param <T>
 */
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Result(){}
	
	public Result(boolean success)
	{
		this.success = success;
	}
	
	private boolean success = false;
	
	private String errorCode;
	
	private String errorMessage;
	
	private String date;
	
	private T obj;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
		this.setSuccess(false);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

}
