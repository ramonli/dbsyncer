package com.hzins.dbsync.impl.domain;

public class AccountCreate {
	private Long userId;
	private Byte fromType;
	private String userName;
	/**
	 * Refer to {@link com.hzins.ac.common.constant.AccountTypeEnum}
	 */
	private Byte accountType;

	public AccountCreate() {
	}

	public AccountCreate(Long userId, Byte fromType, String userName, Byte accountType) {
		super();
		this.userId = userId;
		this.fromType = fromType;
		this.userName = userName;
		this.accountType = accountType;
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
