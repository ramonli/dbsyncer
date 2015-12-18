package com.hzins.dbsync.impl.constant;

public enum CurrencyEnum {
	BALANCE(1001, "现金账户"),
	GOLDEN_COIN(1002, "金币"),
	GOLDEN_BEAN(1003, "金豆"),
	COUPON(1021, "优惠券"),
	RED_ENVELOPE(1022, "红包"),
	BANK_WITHHOLDING(1024, "银行代扣"),
	MONTH_SETTLEMENT(1025, "月结货币");
	
	private int id;
	private String name;

	private CurrencyEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
