package com.hzins.dbsync.impl.constant;

public enum PreDepositType {
	PRECHARGE((byte)1, "预充值"), 
	CONFIRM_PRECHARGE((byte)2, "预充值收款");

	private byte value;
	private String name;

	private PreDepositType(byte value, String name) {
		this.value = value;
		this.name = name;
	}

	public byte getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

}
