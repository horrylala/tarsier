package com.sf.tarsier.mvc.entity;

public class MarketProp {
	private Long id; // 配置表ID
	
	private String propKey; // 配置表键
	
	private String propVal; // 配置表值

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPropKey() {
		return propKey;
	}

	public void setPropKey(String propKey) {
		this.propKey = propKey;
	}

	public String getPropVal() {
		return propVal;
	}

	public void setPropVal(String propVal) {
		this.propVal = propVal;
	}
}
