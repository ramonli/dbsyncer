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
 * 
 * </p>
 * 
 * @author hz15051248
 * @date 2015年8月27日 下午3:50:40
 * @version
 */
public enum CurrencyTypeEnum {
	/** 可累加真实货币，一般是指现金 */
	ACCUMULATE_REAL_CURRENCY((byte) 0, "现金"),
	/** 可累加虚拟货币，比如金豆，金币 */
	ACCUMULATE_VIRTUAL_CURRENCY((byte) 1, "可累加虚拟货币"),
	/** 不可累加货币,只优惠券，红包等，必须一次性用完 */
	NONACCUMULATE_CURRENCY((byte) 2, "不可累加货币"),
	/** 网关支付 */
	GATEWAY_PAY((byte) 3, "网关支付"),
	/** 聚米救援卡,银行代扣 */
	JM_HELPCARD((byte) 4, "聚米救援卡");

	private byte value;
	private String name;

	CurrencyTypeEnum(byte value, String name) {
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
