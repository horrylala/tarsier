package com.sf.tarsier.mvc.system.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sf.tarsier.mvc.system.entity.LoggerType;
import com.sf.tarsier.mvc.system.exception.BusinessException;

public final class HttpUtil {
	
	private HttpUtil(){}
	
	protected static Logger logger = LoggerFactory.getLogger(LoggerType.FILTER);
	
	private static final String CHARSET = "UTF-8";
	
	private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
	
	/**
	 * post请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * @param contentType 请求类型
	 * @return
	 */
	public static String post(String url, Map<String, Object> params, String contentType) {
		return post(url, JSONObject.toJSONString(params), contentType);
	}

	/**
	 * post请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * contentType 请求类型默认"application/json;charset=UTF-8"
	 * @return
	 */
	public static String post(String url, Map<String, Object> params) {
		return post(url, JSONObject.toJSONString(params), CONTENT_TYPE);
	}
	
	/**
	 * post请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * contentType 请求类型默认"application/json;charset=UTF-8"
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String post(String url, String params) {
		return post(url, (Map<String, Object>)JSON.parseObject(params, Map.class), CONTENT_TYPE);
	}
	
	/**
	 * post请求实现
	 * @param url 请求地址
	 * @param params 请求参数
	 * @param contentType 请求类型
	 * @return
	 */
	private static String post(String url, String params, String contentType) {
		StringBuilder result = new StringBuilder();
		HttpURLConnection con = null;
		try {
			URL u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setAllowUserInteraction(false);
			con.setUseCaches(false);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", contentType);
			con.setConnectTimeout(10000); //10秒
			con.setReadTimeout(20000); //20秒
		} catch (Exception e) {
			logger.error("postConnection error : " + url + ", " + params + ", " + contentType, e);
			conDisConnect(con);
			throw new BusinessException("postConnection fail", e);
		}
		if(StringUtils.isNotBlank(params) && !"null".equalsIgnoreCase(params)) {
			try (OutputStream out = con.getOutputStream()){
				out.write(params.getBytes(CHARSET));
				out.flush();
			} catch (Exception e) {
				logger.error("postOutputStream error : " + url + ", " + params + ", " + contentType, e);
				conDisConnect(con);
				throw new BusinessException("postOutputStream fail", e);
			}
		}
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), CHARSET))){
			String line = null;
			while(null != (line = reader.readLine())) {
				result.append(line);
			}
			return result.toString();
		} catch (Exception e) {
			logger.error("postBufferedReader error : " + url + ", " + params + ", " + contentType, e);
			conDisConnect(con);
			throw new BusinessException("postBufferedReader fail", e);
		}
	}
	
	/**
	 * 关闭连接
	 * @param con
	 */
	private static void conDisConnect(HttpURLConnection con)
	{
		if(null != con)
		{
			try {
				con.disconnect();
			} catch (Exception e) {
				logger.error("conDisConnect error", e);
			}
		}
	}

	/**
	 * get请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * @return
	 */
	public static String get(String url, Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		if(!params.isEmpty()) {
			for (Entry<String, Object> param : params.entrySet()) {
				sb.append(param.getKey()).append("=").append(param.getValue()).append("&");
			}
			sb.setLength(sb.length() - 1);
		}
		return get(url, sb.toString());
	}
	
	/**
	 * get请求实现
	 * @param url 请求地址
	 * @param params 请求参数
	 * @return
	 */
	private static String get(String url, String params) {
		StringBuilder result = new StringBuilder();
		HttpURLConnection con = null;
		try {
			URL u = null;
			if(StringUtils.isNotBlank(params))
			{
				u = new URL(url + "?" + params);
			}
			else
			{
				u = new URL(url);
			}
			con = (HttpURLConnection) u.openConnection();
			con.setUseCaches(false);
			con.setRequestMethod("GET");
			con.setConnectTimeout(10000); //10秒
			con.setReadTimeout(20000); //20秒
		} catch (Exception e) {
			logger.error("getConnection error : " + url + ", " + params, e);
			conDisConnect(con);
			throw new BusinessException("getConnection fail", e);
		}
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), CHARSET))){
			String line = null;
			while(null != (line = reader.readLine())) {
				result.append(line);
			}
			return result.toString();
		} catch (Exception e) {
			logger.error("getBufferedReader error : " + url + ", " + params, e);
			conDisConnect(con);
			throw new BusinessException("getBufferedReader fail", e);
		}
	}
}
