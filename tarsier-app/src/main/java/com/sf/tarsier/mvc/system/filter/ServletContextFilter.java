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

	protected static Logger logger = LoggerFactory.getLogger(LoggerType.FILTER);

	private static String servletName = "";

	public void init(FilterConfig config) throws ServletException {
		//设置服务启动时间和启动容器信息
		servletName = config.getServletContext().getServerInfo();
		logger.info("链接拦截器 init .... servletName=["+servletName+"]");
	}

	@Override
	public void destroy() {
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
			
			/*//路径权限判断
			//如果用户session为空，且请求不在透传URLS中，就直接退出
			if("openid" == null && !existsUrl(reqUrl,FilterUrl.getNotNeedAuthUrl()))
			{
				sb.append("无效请求未鉴权，");
				PrintWriter pw = hres.getWriter();
				pw.write(JSON.toJSONString(ResultUtil.error("请先完成鉴权","NOT_AUTH")));
				pw.close();
				return;
			}
			if("session" == null && !existsUrl(reqUrl,FilterUrl.getNotNeedLoginUrl()))
			{
				sb.append("无效请求未登陆，");
				PrintWriter pw = hres.getWriter();
				pw.write(JSON.toJSONString(ResultUtil.error("请先登陆","NOT_LOGIN")));
				pw.close();
				return;
			}*/
			//还可以对特定菜单加权限，只有指定IP或指定人员才可访问
			//更新redis-session重新设置为30分钟
			//UserSession.updateSessionTm(req);
			
			//调用后台处理
			chain.doFilter(hreq, response);
		} finally {
			long end = System.currentTimeMillis();
			sb.append(" 耗时：," + (end - begin) + ",ms ");
			logger.debug(sb.toString());
		}
	}
	
	/**
	 * 检查当前请求是否在过滤url中
	 * @param url
	 * @param urls
	 * @return
	 *//*
	private boolean existsUrl(String url, List<String> urls){
		for (String tmp : urls) {
			if(url.equals(tmp))
			{
				return true;
			}
			if(tmp.endsWith("*") && url.startsWith(tmp.substring(0, tmp.length() - 1))) {
				return true;
			}
		}
		return false;
	}*/
}
