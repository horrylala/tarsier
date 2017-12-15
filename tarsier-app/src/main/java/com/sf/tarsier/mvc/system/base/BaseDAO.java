package com.sf.tarsier.mvc.system.base;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * MyBatis的Dao基类
 */
@Repository
public class BaseDAO extends SqlSessionDaoSupport implements Serializable{

	private static final long serialVersionUID = 185036210962082779L;
	
	@Autowired
	public void setMySessionFactory(SqlSessionFactory mySessionFactory) {
		super.setSqlSessionFactory(mySessionFactory);
	}
	
	public Object selectOne(String key) {
		return selectOne(key,null);
	}
	
	public Object selectOne(String key, Object params) {
		return getSqlSession().selectOne(key, params);
	}
	
	public Object selectList(String key) {
		return selectList(key,null);
	}

	public <T> List<T> selectList(String key, Object params) {
		return getSqlSession().selectList(key, params);
	}

	public void insert(String key, Object params) {
		getSqlSession().insert(key, params);
	}

	public void delete(String key, Object params) {
		getSqlSession().delete(key, params);
	}

	public void update(String key, Object params) {
		getSqlSession().update(key, params);
	}
}
