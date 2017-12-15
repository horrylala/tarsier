package com.sf.tarsier.mvc.system.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.sf.tarsier.mvc.system.entity.LoggerType;


/**
 * 类名:ServletContextFilter
 * 功能: 数据提交拦截，操作记录和超时操作处理和数据去空格处理
 */
public class ServletContextFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(LoggerType.FILTER);

	private String servletName = "";

	public void init(FilterConfig config) throws ServletException {
		//设置服务启动时间和启动容器信息
		servletName = config.getServletContext().getServerInfo();
		logger.info("链接拦截器 init .... servletName=["+servletName+"]");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		long begin = System.currentTimeMillis();

		HttpServletRequest hreq = (HttpServletRequest) request;
		HttpServletResponse hres = (HttpServletResponse) response;
		hres.setContentType("text/html;charset=UTF-8");

		StringBuilder sb = new StringBuilder();
		String key = Thread.currentThread().getId() + "$" + begin;
		String reqUrl = hreq.getRequestURI();
		
		sb.append("key=["+key+"]");
		sb.append("sessionId=["+hreq.getSession().getId()+"]");
		sb.append("servletName=["+servletName+"]");
		sb.append("访问路径：[" + reqUrl + "]");
		sb.append("访问类型：[" + hreq.getMethod() + " / " + hreq.getContentType() + "]");
		try{
			//入参记录
			if("POST".equalsIgnoreCase(hreq.getMethod()))
			{
				// 防止流读取一次后就没有了, 所以需要将流继续写出去
				hreq = new HttpServletRequestReaderParamWrapper(hreq);
				sb.append("入参：[").append(HttpServletRequestReaderParamWrapper.getBodyString(hreq)).append("]");
			} 
			else 
			{
				sb.append("入参：[").append(JSON.toJSONString(hreq.getParameterMap())).append("]");
				// 去空格处理
				hreq = new HttpServletRequestTrimParamWrapper(hreq);
			}
			
			//调用后台处理
			chain.doFilter(hreq, response);
		} finally {
			long end = System.currentTimeMillis();
			sb.append(" 耗时：," + (end - begin) + ",ms ");
			logger.debug(sb.toString());
		}
	}

	@Override
	public void destroy() {
		logger.debug("销毁对象");
	}
	
}
