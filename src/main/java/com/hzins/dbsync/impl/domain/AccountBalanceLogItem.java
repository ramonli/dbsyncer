package com.hzins.dbsync.impl.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.hzins.dbsync.impl.constant.OperationTypeEnum;

public class AccountBalanceLogItem {
	public static final int OPERATOR_TYPE_RECHARGE = OperationTypeEnum.RECHARGE.getValue();

	private Long userId;
	private Integer fromType;
	private Integer currencyId;
	private BigDecimal amount;
	private Long cnyAmount;
	private BigDecimal balanceAfterTrans;
	private BigDecimal balanceBeforeTrans;
	private Date createTime;
	private Date updateTime;
	/**
	 * 收支类型(1:收入，2：支出，3：非收入非支出). Refer to {@link com.hzins.ac.common.constant.IncomeExpenditureTypeEnum}
	 */
	private Integer ieType;
	/**
	 * 应该只包括充值和赠送（但是赠送可能有很多类型，如果是赠送冻结，那么需要作为<code>AccountBalanceFreeze</code>的记录来迁移）。Refer to {@link com.hzins.ac.common.constant.OperationTypeEnum}
	 */
	private Integer operationType;
	private String remark;
	private String transactionId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getFromType() {
		return fromType;
	}

	public void setFromType(Integer fromType) {
		this.fromType = fromType;
	}

	public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getCnyAmount() {
		return cnyAmount;
	}

	public void setCnyAmount(Long cnyAmount) {
		this.cnyAmount = cnyAmount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getOperationType() {
		return operationType;
	}

	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIeType() {
		return ieType;
	}

	public void setIeType(Integer ieType) {
		this.ieType = ieType;
	}

	public BigDecimal getBalanceAfterTrans() {
		return balanceAfterTrans;
	}

	public void setBalanceAfterTrans(BigDecimal balanceAfterTrans) {
		this.balanceAfterTrans = balanceAfterTrans;
	}

	public BigDecimal getBalanceBeforeTrans() {
		return balanceBeforeTrans;
	}

	public void setBalanceBeforeTrans(BigDecimal balanceBeforeTrans) {
		this.balanceBeforeTrans = balanceBeforeTrans;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

}
