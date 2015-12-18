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
 * 来源平台
 * 
 * 账户类型枚举
 * </p>
 * 
 * @author hz15051248
 * @date 2015年8月27日 下午3:50:40
 * @version
 */
public enum AccountFromTypeEnum {
	/** 慧择 */
	HZ((byte)1, "慧择"),
	
	/** 渠道 */
	CHANNEL((byte)2, "渠道"),
	/** 聚米 */
	JM((byte)3, " 聚米"),
	/** 保运通 */
	BYT((byte)4, "保运通");

	private byte value;
	private String name;

	AccountFromTypeEnum(byte value, String name) {
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
