package com.sf.tarsier.mvc.system.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sf.tarsier.mvc.system.entity.LoggerType;
import com.sf.tarsier.mvc.system.exception.BusinessException;

public final class HttpUtils {
	private static final Logger logger = Logger.getLogger(LoggerType.FILTER);
	public static final String UTF_8 = "UTF-8";

	private HttpUtils() {
	}

	/**
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数map
	 * @param contentType
	 *            类型
	 * @return
	 */
	public static Object post(String url, Map<String, Object> params, String contentType) {
		return post(url, JSONObject.toJSONString(params), contentType);
	}

	/**
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数map </br>
	 *            默认 contentType:application/json;charset=UTF-8
	 * @return
	 */
	public static Object post(String url, Map<String, Object> params) {
		return post(url, params, "application/json;charset=UTF-8");
	}

	public static String get(String url, Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, Object> param : params.entrySet()) {
			sb.append(param.getKey()).append("=").append(param.getValue()).append("&");
		}
		sb.setLength(sb.length() - 1);
		return get(url, sb.toString());
	}

	/**
	 * 
	 * @param url
	 * @param contentType
	 *            请求参数类型
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static Object post(String url, String params, String contentType) {
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
			con.setConnectTimeout(10000);
			con.setReadTimeout(20000);
		} catch (Exception e) {
			logger.error("postConnection请求失败：" + url + "," + params, e);
			conDisConnect(con);
			throw new BusinessException("postConnection请求失败：", e);
		}
		if (!StringUtils.isEmpty(params) && !"null".equals(params)) {
			try (OutputStream out = con.getOutputStream();) {
				out.write(params.getBytes(UTF_8));
				out.flush();
			} catch (Exception e) {
				logger.error("postOutputStream请求失败：" + url + "," + params, e);
				conDisConnect(con);
				throw new BusinessException("postOutputStream请求失败：", e);
			}
		}
		File f=new File("E:\\11.png");
		try (InputStream is=con.getInputStream();OutputStream os=new FileOutputStream(f);){
//			byte[] data= new byte[is.available()];
//			is.read(data);
//			return data;
			byte[] bytes = new byte[1024];
            int length = 0;
            while ((length = is.read(bytes)) != -1) {
                os.write(bytes, 0, length);
            }
            os.flush();
            return f.getAbsolutePath();
//			String ret=Base64.byteArrayToBase64(data);
//			logger.info("key=[" + Thread.currentThread().getId() + "$]" + ret);
//			return ret;
		} catch (Exception e) {
			logger.error("post请求失败：" + url + "," + params, e);
			throw new BusinessException("post请求失败：", e);
		} finally {
			conDisConnect(con);
		}
	}

	private static void conDisConnect(HttpURLConnection con) {
		if (null != con) {
			try {
				con.disconnect();
			} catch (Exception e2) {
				logger.error(e2.getMessage(), e2);
			}
		}
	}

	/**
	 * 
	 * 发送GET请求
	 * 
	 * @param url
	 *            请求url
	 * @param params
	 *            get参数 key1=val1&key2=val2&key3=val3
	 * @return
	 * @throws IOException
	 */
	public static String get(String url, String params) {
		StringBuilder result = new StringBuilder();
		HttpURLConnection con = null;
		try {
			URL u = new URL(url + "?" + params);
			con = (HttpURLConnection) u.openConnection();
			con.setUseCaches(false);
		} catch (Exception e) {
			logger.error("getConnection请求失败：" + url + "," + params, e);
			if (null != con) {
				try {
					con.disconnect();
				} catch (Exception e2) {
					logger.error(e2.getMessage(), e2);
				}
			}
			throw new BusinessException("getConnection请求失败：", e);
		}
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), UTF_8));) {
			String line = null;
			while (null != (line = reader.readLine())) {
				result.append(line);
			}
			String ret = result.toString();
			logger.info("key=[" + Thread.currentThread().getId() + "$]" + ret);
			return ret;
		} catch (Exception e) {
			logger.error("get请求失败：" + url + "," + params, e);
			throw new BusinessException("get请求失败：", e);
		} finally {
			try {
				con.disconnect();
			} catch (Exception e2) {
				logger.error(e2.getMessage(), e2);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Object post(String url, String paramsJson) {
		Map<String, Object> map = JSON.parseObject(paramsJson, Map.class);
		return post(url, map);
	}
}
