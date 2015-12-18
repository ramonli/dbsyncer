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
 * account_operation_request_log 操作类型. 账户中心定义的操作类型从100开始，避免与业务线定义的操作类型冲突.
 * </p>
 */
public enum OperationTypeEnum {
	INSURE_EXPENDITURE((byte) 101, "投保支出"),
	REFUND((byte) 102, "退款"),
	RECHARGE((byte) 103, "充值"),
	AWARD_PAYMENT((byte) 104, "支付赠送"),
	REVERSAL((byte) 105, "冲正"),
	PRE_RECHARGE((byte) 106, "预充值"),
	PREDEPOSITREPAYMENT((byte) 107, "预充值还款"),
	PAY_FREEZE((byte) 108, "网关支付冻结"),
	UNFREEZE((byte) 109, "解冻"),
	REFUND_FREEZE((byte) 110, "退款冻结"),
	REFUND_UNFREEZE((byte) 111, "退款解冻"),
	DEDUCT_AWARD((byte) 112, "扣除赠送"),
	USED((byte) 113, "已使用"),
	FAIL((byte) 114, "已失效"),
	EXCEPTIONCALLBACK((byte) 115, "交易回退"),
	PAYMENTEXPRESSFEE((byte) 116, "支付快递费"),
	REFUNDEXPRESSFEE((byte) 117, "退款快递费"),
	DECUCT_OF_CHANNEL((byte)118,"渠道扣除"),
	AWARD_OF_JM((byte)119,"聚米赠送"),
	AWARD_OF_CHANNEL((byte)120,"渠道赠送"),
	PAY_OK_UNFREEZRE((byte)121, "网关支付成功解冻"),
	PAY_FAIL_UNFREEXE((byte)122, "网关支付失败解冻"),
	FINANCE_INCOME((byte)123,"财务入账");

	private byte value;
	private String name;

	OperationTypeEnum(byte value, String name) {
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
