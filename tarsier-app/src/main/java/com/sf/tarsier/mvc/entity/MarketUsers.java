package com.sf.tarsier.mvc.entity;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class MarketUsers {
	private Long id; //主键
	
	@NotNull(message = "专业市场不能为空")
	@NotEmpty(message = "专业市场不能为空")
	private String mktId; //专业市场ID
	
	@NotNull(message = "寄件地址不能为空")
	@NotEmpty(message = "寄件地址不能为空")
	private String addr; //寄件地址
	
	@NotNull(message = "寄件人名称不能为空")
	@NotEmpty(message = "寄件人名称不能为空")
	private String userName; //寄件人名称
	
	@NotNull(message = "寄件人电话不能为空")
	@NotEmpty(message = "寄件人电话不能为空")
	private String mobile; //寄件人电话
	
	@NotNull(message = "寄件数量不能为空")
	@Min(1)
	@Max(1000)
	private Long senderNum; //寄件数量
	
	@NotNull(message = "平均重量不能为空")
	@Min(1)
	@Max(1000)
	private Double weight; //平均重量
	
	private String imageUrl; //头像
	
	private Date createTm; //创建时间
	
	private Date updateTm; //更新时间
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMktId() {
		return mktId;
	}

	public void setMktId(String mktId) {
		this.mktId = mktId;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getSenderNum() {
		return senderNum;
	}

	public void setSenderNum(Long senderNum) {
		this.senderNum = senderNum;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Date getCreateTm() {
		return createTm;
	}

	public void setCreateTm(Date createTm) {
		this.createTm = createTm;
	}

	public Date getUpdateTm() {
		return updateTm;
	}

	public void setUpdateTm(Date updateTm) {
		this.updateTm = updateTm;
	}

}
