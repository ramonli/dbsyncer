package com.hzins.dbsync.impl.domain;

import java.math.BigDecimal;
import java.util.Date;

public class AccountBalanceItem {
	private Integer currencyId;
	private BigDecimal amount;
	private Long cnyAmount;
	private Date createTime;
	private Date updateTime;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
