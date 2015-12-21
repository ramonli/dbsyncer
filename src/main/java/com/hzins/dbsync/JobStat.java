package com.hzins.dbsync;

import java.util.concurrent.atomic.AtomicLong;

public class JobStat {
	private AtomicLong readAccountCount = new AtomicLong(0);
	private AtomicLong writeAccountCount = new AtomicLong(0);
	private AtomicLong readBalanceCount = new AtomicLong(0);
	private AtomicLong writeBalanceCount = new AtomicLong(0);
	private AtomicLong readBalanceFundLogCount = new AtomicLong(0);
	private AtomicLong readGoldBeanFundLogCount = new AtomicLong(0);
	private AtomicLong writeFundLogCount = new AtomicLong(0);
	private AtomicLong duplicatedAccountCount = new AtomicLong(0);

	public AtomicLong getReadAccountCount() {
		return readAccountCount;
	}

	public void setReadAccountCount(AtomicLong readAccountCount) {
		this.readAccountCount = readAccountCount;
	}

	public AtomicLong getWriteAccountCount() {
		return writeAccountCount;
	}

	public void setWriteAccountCount(AtomicLong writeAccountCount) {
		this.writeAccountCount = writeAccountCount;
	}

	public AtomicLong getReadBalanceCount() {
		return readBalanceCount;
	}

	public void setReadBalanceCount(AtomicLong readBalanceCount) {
		this.readBalanceCount = readBalanceCount;
	}

	public AtomicLong getWriteBalanceCount() {
		return writeBalanceCount;
	}

	public void setWriteBalanceCount(AtomicLong writeBalanceCount) {
		this.writeBalanceCount = writeBalanceCount;
	}

	public AtomicLong getReadBalanceFundLogCount() {
		return readBalanceFundLogCount;
	}

	public void setReadBalanceFundLogCount(AtomicLong readBalanceFundLogCount) {
		this.readBalanceFundLogCount = readBalanceFundLogCount;
	}

	public AtomicLong getReadGoldBeanFundLogCount() {
		return readGoldBeanFundLogCount;
	}

	public void setReadGoldBeanFundLogCount(AtomicLong readGoldBeanFundLogCount) {
		this.readGoldBeanFundLogCount = readGoldBeanFundLogCount;
	}

	public AtomicLong getWriteFundLogCount() {
		return writeFundLogCount;
	}

	public void setWriteFundLogCount(AtomicLong writeFundLogCount) {
		this.writeFundLogCount = writeFundLogCount;
	}

	public AtomicLong getDuplicatedAccountCount() {
		return duplicatedAccountCount;
	}

	public void setDuplicatedAccountCount(AtomicLong duplicatedAccountCount) {
		this.duplicatedAccountCount = duplicatedAccountCount;
	}

}
