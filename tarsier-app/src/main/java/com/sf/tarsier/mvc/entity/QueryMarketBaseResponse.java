package com.sf.tarsier.mvc.entity;

import java.util.List;
import java.util.Map;

public class QueryMarketBaseResponse {

	/**
	 * 参团人数
	 */
	private Integer userCount;

	/**
	 * 剩余人数
	 * 
	 * @return
	 */
	private Integer freeCount;

	/**
	 * 占比
	 * 
	 * @return
	 */
	private Integer completePercent;

	/**
	 * 截止日期
	 */
	private String deadline;

	/**
	 * 拼团信息
	 */
	private MarketBase marketBase;

	/**
	 * 参团列表
	 * 
	 * @return
	 */
	private List<Map<String, String>> users;

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

	public List<Map<String, String>> getUsers() {
		return users;
	}

	public void setUsers(List<Map<String, String>> users) {
		this.users = users;
	}

	public Integer getFreeCount() {
		return freeCount;
	}

	public void setFreeCount(Integer freeCount) {
		this.freeCount = freeCount;
	}

	public Integer getCompletePercent() {
		return completePercent;
	}

	public void setCompletePercent(Integer completePercent) {
		this.completePercent = completePercent;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
}
