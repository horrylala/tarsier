package com.sf.tarsier.mvc.system.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * post时，读取并备份流数据入参
 */
public class HttpServletRequestReaderParamWrapper extends HttpServletRequestWrapper{
	
	private final byte[] body;
	
	public HttpServletRequestReaderParamWrapper(HttpServletRequest request) {
		super(request);
		body = getBodyString(request).getBytes(Charset.forName("UTF-8"));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream ba = new ByteArrayInputStream(body);
		
		return new ServletInputStream() {
			
			@Override
			public int read() throws IOException {
				return ba.read();
			}
			
			@Override
			public void setReadListener(ReadListener listener) {
				
			}
			
			@Override
			public boolean isReady() {
				return false;
			}
			
			@Override
			public boolean isFinished() {
				return false;
			}
		};
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	/**
	 * 获取请求内容
	 * @param request
	 * @return
	 */
	public static String getBodyString(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		try (InputStream is = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));){
			String line = "";
			while(null != (line = reader.readLine())) {
				sb.append(line);
			}
		} catch (IOException e) {
			// handle exception
		}
		return sb.toString();
	}

}
