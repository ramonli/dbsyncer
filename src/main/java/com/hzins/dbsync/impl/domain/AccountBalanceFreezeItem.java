package com.hzins.dbsync.impl.domain;

import java.math.BigDecimal;

public class AccountBalanceFreezeItem {
	private Long userId;
	private Integer fromType;
	private Integer currencyId;
	private BigDecimal amount;
	private Long cnyAmount;
	private Byte freezeStatus;
	private String thawTime;
	private String createTime;
	private String updateTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getFromType() {
		return fromType;
	}

	public void setFromType(Integer fromType) {
		this.fromType = fromType;
	}

	public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getCnyAmount() {
		return cnyAmount;
	}

	public void setCnyAmount(Long cnyAmount) {
		this.cnyAmount = cnyAmount;
	}

	public Byte getFreezeStatus() {
		return freezeStatus;
	}

	public void setFreezeStatus(Byte freezeStatus) {
		this.freezeStatus = freezeStatus;
	}

	public String getThawTime() {
		return thawTime;
	}

	public void setThawTime(String thawTime) {
		this.thawTime = thawTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
