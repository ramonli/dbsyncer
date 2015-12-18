/**
 * Copyright (c) 2006-2015 Hzins Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Hzins,http://www.hzins.com.
 *  
 */
package com.hzins.dbsync.impl.constant;

/**
 * <p>
 * 
 * account_fund_operation_record 记录类型枚举
 * </p>
 * 
 * @author hz15051248
 * @date 2015年8月27日 下午3:50:40
 * @version
 */
public enum RecordTypeEnum {
	/** account_balance_freeze */
	ACCOUNT_BALANCE_FREEZE((byte) 1, "用户资金冻结表"),
	/** voucher */
	VOUCHER((byte) 2, "用户劵表"), 
	ACCOUNT_BALANCE((byte) 3, "账户余额表"),
	/** 回滚的时候会记录回滚的那条资金流水记录 */
	ACCOUNT_FUND_OPERATION((byte)4, "资金流水表"),
	/** pre_deposit */
	PRE_DEPOSIT((byte) 7, "预充值表");

	private byte value;
	private String name;

	RecordTypeEnum(byte value, String name) {
		this.value = value;
		this.name = name;
	}

	public byte getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public void setValue(Byte value) {
		this.value = value;
	}

	public void setName(String name) {
		this.name = name;
	}

}
