package com.sf.tarsier.mvc.entity;

import java.util.Date;

public class MarketBase {
	/**
	 * 专业市场ID
	 */
	private String mktId;
	/**
	 * 市场外部名称
	 */
	private String mktNameShow;
	/**
	 * 单客日均最小件量
	 */
	private Integer dailyMinPackages;
	/**
	 * 重量区间（最小）
	 */
	private Double weightMin;
	/**
	 * 重量区间（最大）
	 */
	private Double weightMax;
	/**
	 * 首重价格
	 */
	private Double basePrice;
	/**
	 * 首重重量
	 */
	private Double baseWeight;
	/**
	 * 成团人数
	 */
	private Integer groupLimit;
	/**
	 * 拼团周期
	 */
	private Integer groupDuration;
	/**
	 * 使用要求
	 */
	private String useRequire;

	/**
	 * 创建时间
	 */
	private Date createTime;

	public String getMktId() {
		return mktId;
	}

	public void setMktId(String mktId) {
		this.mktId = mktId;
	}

	public String getMktNameShow() {
		return mktNameShow;
	}

	public void setMktNameShow(String mktNameShow) {
		this.mktNameShow = mktNameShow;
	}

	public Integer getDailyMinPackages() {
		return dailyMinPackages;
	}

	public void setDailyMinPackages(Integer dailyMinPackages) {
		this.dailyMinPackages = dailyMinPackages;
	}

	public Double getWeightMin() {
		return weightMin;
	}

	public void setWeightMin(Double weightMin) {
		this.weightMin = weightMin;
	}

	public Double getWeightMax() {
		return weightMax;
	}

	public void setWeightMax(Double weightMax) {
		this.weightMax = weightMax;
	}

	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	public Double getBaseWeight() {
		return baseWeight;
	}

	public void setBaseWeight(Double baseWeight) {
		this.baseWeight = baseWeight;
	}

	public Integer getGroupLimit() {
		return groupLimit;
	}

	public void setGroupLimit(Integer groupLimit) {
		this.groupLimit = groupLimit;
	}

	public Integer getGroupDuration() {
		return groupDuration;
	}

	public void setGroupDuration(Integer groupDuration) {
		this.groupDuration = groupDuration;
	}

	public String getUseRequire() {
		return useRequire;
	}

	public void setUseRequire(String useRequire) {
		this.useRequire = useRequire;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
