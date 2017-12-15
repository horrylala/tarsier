package com.sf.tarsier.mvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sf.tarsier.mvc.entity.MarketUsers;
import com.sf.tarsier.mvc.system.base.BaseService;
import com.sf.tarsier.mvc.system.entity.Book;
import com.sf.tarsier.mvc.system.entity.LoggerType;
import com.sf.tarsier.mvc.system.entity.Result;
import com.sf.tarsier.mvc.system.util.ResultUtil;

@Service("marketUsersService")
public class MarketUsersService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(LoggerType.COMMON);
	
	/**
	 * <b>功能：保存集货拼团，参团信息
	 */
	public Result<Object> saveMarketUsers(MarketUsers users) {
		try {
			
			/*int val = (int) getBaseDAO().selectOne("BookMapper.selectTest", null);
			List<?> vals = getBaseDAO().selectList("BookMapper.selectMap", book);
			String result = JSON.toJSONString(vals);
			logger.info("firist result is {}", result);
			
			Map<String,String> tmp = new HashMap<>();
			tmp.put("id", String.valueOf(book.getId()));
			tmp.put("bookName", book.getBookName());
			List<?> vals2 = getBaseDAO().selectList("BookMapper.selectFromMap", tmp);
			result = JSON.toJSONString(vals2);
			logger.info("second result is {}", result);*/
			return ResultUtil.success();
		} catch (Exception e) {
			logger.error("测试信息失败", e);
		}
		return ResultUtil.success();
	}
}
