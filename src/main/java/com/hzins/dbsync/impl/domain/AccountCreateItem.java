package com.hzins.dbsync.impl.domain;

import java.util.Date;

public class AccountCreateItem {
	private Long userId;
	private Byte fromType;
	private String userName;
	/**
	 * Refer to {@link com.hzins.ac.common.constant.AccountTypeEnum}
	 */
	private Byte accountType;
	private Date createTime;
	private Date updateTime;

	public AccountCreateItem() {
	}

	public AccountCreateItem(Long userId, Byte fromType, String userName, Byte accountType) {
		super();
		this.userId = userId;
		this.fromType = fromType;
		this.userName = userName;
		this.accountType = accountType;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Byte getFromType() {
		return fromType;
	}

	public void setFromType(Byte fromType) {
		this.fromType = fromType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Byte getAccountType() {
		return accountType;
	}

	public void setAccountType(Byte accountType) {
		this.accountType = accountType;
	}

}
