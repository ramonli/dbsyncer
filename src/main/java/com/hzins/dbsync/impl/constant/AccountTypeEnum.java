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
 * 
 * 账户类型枚举
 * </p>
 * 
 * @author hz15051248
 * @date 2015年8月27日 下午3:50:40
 * @version
 */
public enum AccountTypeEnum {
	/**
	 * 月结账户。对于月结账户，余额账户可以为负数，因为是先消费，然后每月进行一次清账。
	 */
	MONTH_SETTLEMENT((byte) 2, "月结账户"),
	/** 非月结账户 */
	NOMONTH_SETTLEMENT((byte) 1, "非月结账户");

	private byte value;
	private String name;

	AccountTypeEnum(byte value, String name) {
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
