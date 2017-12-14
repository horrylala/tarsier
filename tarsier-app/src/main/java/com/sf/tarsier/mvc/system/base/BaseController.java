package com.sf.tarsier.mvc.system.base;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.tarsier.mvc.system.entity.LoggerType;
import com.sf.tarsier.mvc.system.util.ResultUtil;


/**
 * SpringMVC中Controller默认都是单动作入口，所有连接共用此类和变量，所以禁止在Controller定义公共变量，
 * 如： private String flag;
 */
public class BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoggerType.COMMON);
	
	/**
	 * 时间格式绑定 yyyy-MM-dd
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(
				dateFormat, true));// false表示不能为空
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Object handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
		logger.error("系统异常:" + Thread.currentThread().getId() + "$" + request.getRequestURI(), e);
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		response.setStatus(status.value());
		return ResultUtil.error(status.name(), String.valueOf(status.value()));
	}
}
