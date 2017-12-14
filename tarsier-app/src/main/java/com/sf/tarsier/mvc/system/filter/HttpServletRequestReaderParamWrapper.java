package com.sf.tarsier.mvc.system.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;

import com.sf.tarsier.mvc.system.entity.LoggerType;


/**
 * 描述：
 * 
 * <pre>HISTORY
 * ****************************************************************************
 *  ID   DATE           PERSON          REASON
 *  1    2017年8月20日      01238551         Create
 * ****************************************************************************
 * </pre>
 * @author 01238551
 * @since 1.0
 */
public class HttpServletRequestReaderParamWrapper extends HttpServletRequestWrapper {
	
	private static final Logger logger = Logger.getLogger(LoggerType.FILTER);

	private final byte[] body;
	
	public HttpServletRequestReaderParamWrapper(HttpServletRequest request) {
		super(request);
		body = getBodyString(request).getBytes(Charset.forName("UTF-8"));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(body);

        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            	//nonthing to do
            }
        };
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	/**
     * 获取请求Body
     *
     * @param request
     * @return
     */
    public static String getBodyString(ServletRequest request) {
    	StringBuilder sb = new StringBuilder();
    	try (InputStream inputStream = request.getInputStream();
        	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));){
    		String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
		} catch (IOException e) {
			logger.error("获取请求Body异常",e);
		}
        return sb.toString();
    }
}
