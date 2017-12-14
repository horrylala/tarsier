package com.sf.tarsier.mvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sf.tarsier.mvc.system.base.BaseService;
import com.sf.tarsier.mvc.system.entity.Book;
import com.sf.tarsier.mvc.system.entity.LoggerType;

@Service("bookService")
public class BookService extends BaseService<Object> {
	
	protected static Logger logger = LoggerFactory.getLogger(LoggerType.COMMON);
	
	@Value("${datasource.password}")
	private String pass;
	
	/**
	 * <b>功能：测试
	 */
	public int selectTest(Book book) {
		try {
			logger.info("@value "+pass);
			int val = (int) getBaseDAO().selectOne("BookMapper.selectTest", null);
			List<?> vals = (List<?>) getBaseDAO().selectList("BookMapper.selectMap", book);
			logger.info("result is " + JSON.toJSONString(vals));
			
			Map<String,String> tmp = new HashMap<>();
			tmp.put("id", String.valueOf(book.getId()));
			tmp.put("bookName", book.getBookName());
			List<?> vals2 = (List<?>) getBaseDAO().selectList("BookMapper.selectFromMap", tmp);
			logger.info("result2 is " + JSON.toJSONString(vals2));
			return val;
		} catch (Exception e) {
			logger.error("测试信息失败", e);
		}
		return 0;
	}
	
	
	public int getCount() {
		try {
			int count = (int) getBaseDAO().selectOne("BookMapper.getCount", null);
			logger.info("result is {}" , JSON.toJSONString(count));
			return count;
		} catch (Exception e) {
			logger.error("测试信息失败", e);
		}
		return 0;
	}

}
