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
 * Account status definition
 */
public enum AccountStatusEnum {
	NORMAL((byte) 1, "正常的"), STOPPED((byte) 2, "停用的"), CANCELLED((byte) 3, "注销的");

	private byte value;
	private String name;

	AccountStatusEnum(byte value, String name) {
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
