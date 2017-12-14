package com.sf.tarsier.mvc.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sf.tarsier.mvc.system.base.BaseService;
import com.sf.tarsier.mvc.system.entity.LoggerType;

@Service("bookService")
public class BookService extends BaseService<Object> {
	
	protected static Logger logger = LoggerFactory.getLogger(LoggerType.COMMON);
	
	@Value("${datasource.password}")
	private String pass;
	
	/**
	 * <b>功能：测试
	 */
	public int selectTest() {
		try {
			logger.info("@value "+pass);
			int val = (int) getBaseDAO().selectOne("BookMapper.selectTest", null);
			List<?> vals = (List<?>) getBaseDAO().selectList("BookMapper.selectMap", null);
			logger.info("result is " + JSON.toJSONString(vals));
			return val;
		} catch (Exception e) {
			logger.error("测试信息失败", e);
		}
		return 0;
	}

}
