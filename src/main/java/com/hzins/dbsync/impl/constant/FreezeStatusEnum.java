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

public enum FreezeStatusEnum {
	/** 异常回滚 */
	EXCEPTION_CALLBACK((byte) 0, "异常回滚"),
	/** 赠送冻结中 */
	GIVING_FREEZED((byte) 1, "赠送冻结中"),
	/** 解冻 */
	UNFREEZED((byte) 2, "已解冻"),
	/** 冻结 */
	FREEZED((byte) 3, "支付冻结中"),
	/** 已使用 */
	USED((byte) 4, "已使用"),
	/** 已失效 */
	FAILED((byte) 5, "已失效");

	private byte value;
	private String name;

	FreezeStatusEnum(byte value, String name) {
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
