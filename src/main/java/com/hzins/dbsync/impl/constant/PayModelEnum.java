package com.hzins.dbsync.impl.constant;

public enum PayModelEnum {

    TRANSFER((byte) 0, "转账汇款"),
    ALI_PAY((byte) 1, "支付宝支付"),
    CHINA_BANK((byte) 2, "网银在线"),
    UNION_PAY((byte) 3, "银联"),
    _99_BILL((byte) 10, "快钱"),
    YEE_PAY((byte) 12, "易宝在线"),
    YEE_PAY_EPOS((byte) 13, "易宝Epos无卡支付"),
    TEN_PAY((byte) 14, "财付通"),
    MOBILE_PAY((byte) 15, "手机支付"),
    MONTH_SETTLE((byte) 17, "月结"),
    UNION_B2B((byte) 16, "银联(B2B)"),
    WECHAT_PAY((byte) 21, "微信支付"),
    BALANCE((byte) -1, "余额支付"),
    GOLDEN_COIN((byte) 19, "金币"),
    GOLDEN_BEAN((byte) -5, "金豆支付"),
    COUPON((byte) -2, "序列号"),
    RED_ENVELOPE((byte) -14, "红包支付"),
    JM_RESCUE_CARD((byte) -4, "科技公司"),
    MICRO_PAYMENT((byte) -10, "小额支付"),
    BANK_WITHHOLDING((byte) -11, "银行代扣"),
    REFUND_RECHANGE_BY_MEMBER((byte) -12, "退保重出(会员)"),
    REFUND_RECHANGE_BY_NON_MEMBER((byte) -3, "退保重出(非会员)");

    private final byte value;

    private final String desc;

    public byte getValue() {
	return value;
    }

    public String getDesc() {
	return desc;
    }

    private PayModelEnum(byte value, String desc) {
	this.value = value;
	this.desc = desc;
    }

    public static PayModelEnum getByValue(Byte value) {
	PayModelEnum payTypeEnum = null;
	if (null != value) {
	    switch (value) {
		case 0:
		    payTypeEnum = TRANSFER;
		    break;
		case 1:
		    payTypeEnum = ALI_PAY;
		    break;
		case 2:
		    payTypeEnum = CHINA_BANK;
		    break;
		case 3:
		    payTypeEnum = UNION_PAY;
		    break;
		case 10:
		    payTypeEnum = _99_BILL;
		    break;
		case 12:
		    payTypeEnum = YEE_PAY;
		    break;
		case 13:
		    payTypeEnum = YEE_PAY_EPOS;
		    break;
		case 14:
		    payTypeEnum = TEN_PAY;
		    break;
		case 15:
		    payTypeEnum = MOBILE_PAY;
		    break;
		case 17:
		    payTypeEnum = MONTH_SETTLE;
		    break;
		case 16:
		    payTypeEnum = UNION_B2B;
		    break;
		case 21:
		    payTypeEnum = WECHAT_PAY;
		    break;
		case -1:
		    payTypeEnum = BALANCE;
		    break;
		case 19:
		    payTypeEnum = GOLDEN_COIN;
		    break;
		case -5:
		    payTypeEnum = GOLDEN_BEAN;
		    break;
		case -2:
		    payTypeEnum = COUPON;
		    break;
		case -14:
		    payTypeEnum = RED_ENVELOPE;
		    break;
		case -4:
		    payTypeEnum = JM_RESCUE_CARD;
		    break;
		case -10:
		    payTypeEnum = MICRO_PAYMENT;
		    break;
		case -11:
			payTypeEnum = BANK_WITHHOLDING;
			break;
		case -12:
		    payTypeEnum = REFUND_RECHANGE_BY_MEMBER;
		    break;
		case -3:
		    payTypeEnum = REFUND_RECHANGE_BY_NON_MEMBER;
		    break;
		default:
		    break;
	    }
	}

	return payTypeEnum;
    }
}
