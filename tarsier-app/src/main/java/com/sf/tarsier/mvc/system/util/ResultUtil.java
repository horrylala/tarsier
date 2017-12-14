package com.sf.tarsier.mvc.system.util;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import com.sf.tarsier.mvc.system.entity.Result;


public class ResultUtil implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 返回成功 ，不带输出结果
	 */
	public static <T> Result<T> success(){
		return success(null);
	}
	
	/**
	 * 返回成功 ，带输出结果
	 * @param obj
	 * @return
	 */
	public static <T> Result<T> success(T obj){
		Result<T> result = new Result<>(true);
		result.setDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		result.setObj(obj);
		return result;
	}

	/**
	 * 返回失败 ，带错误信息
	 */
	public static <T> Result<T> error(String errorMessage){
		return error(errorMessage, null);
	}
	
	/**
	 * 返回失败 ，带错误信息和错误码
	 * @param obj
	 * @return
	 */
	public static <T> Result<T> error(String errorMessage, String errorCode){
		Result<T> result = new Result<>(false);
		result.setDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		result.setErrorCode(errorCode);
		result.setErrorMessage(errorMessage);
		return result;
	}
}
