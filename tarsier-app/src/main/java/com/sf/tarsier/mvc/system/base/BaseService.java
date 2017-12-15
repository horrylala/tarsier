package com.sf.tarsier.mvc.system.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseService {
	
	@Autowired
	private BaseDAO baseDAO;

	protected BaseDAO getBaseDAO() {
		return baseDAO;
	}
	
	public Object selectOne(String key) {
		return selectOne(key,null);
	}
	
	public Object selectOne(String key, Object params) {
		return getBaseDAO().selectOne(key, params);
	}
	
	public <T> List<T> selectList(String key) {
		return selectList(key, null);
	}

	public <T> List<T> selectList(String key, Object params) {
		return getBaseDAO().selectList(key, params);
	}
}
