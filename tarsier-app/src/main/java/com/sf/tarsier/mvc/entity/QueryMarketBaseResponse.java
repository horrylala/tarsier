package com.sf.tarsier.mvc.entity;

import java.util.List;


public class QueryMarketBaseResponse {
	/**
	 * 拼团信息
	 */
	private MarketBase marketBase;
	
	/**
	 * 参团人数
	 */
	private Integer userCount;

	/**
	 * 参团列表
	 * @return
	 */
	private List<MarketUsers> users;

	public MarketBase getMarketBase() {
		return marketBase;
	}

	public void setMarketBase(MarketBase marketBase) {
		this.marketBase = marketBase;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	public List<MarketUsers> getUsers() {
		return users;
	}

	public void setUsers(List<MarketUsers> users) {
		this.users = users;
	}
}
