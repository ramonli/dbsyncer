package com.hzins.dbsync.impl.domain;

import java.util.LinkedList;
import java.util.List;

public class AccountMigration {
	private long accountId;
	private AccountCreateItem accountCreateItem;
	private List<AccountBalanceItem> balanceItems = new LinkedList<AccountBalanceItem>();
	private List<AccountBalanceFreezeItem> balanceFreezeItems = new LinkedList<AccountBalanceFreezeItem>();
	private List<AccountBalanceLogItem> balanceLogItems = new LinkedList<AccountBalanceLogItem>();

	public AccountCreateItem getAccountCreateItem() {
		return accountCreateItem;
	}

	public void setAccountCreateItem(AccountCreateItem accountCreateItem) {
		this.accountCreateItem = accountCreateItem;
	}

	public List<AccountBalanceItem> getBalanceItems() {
		return balanceItems;
	}

	public void setBalanceItems(List<AccountBalanceItem> balanceItems) {
		this.balanceItems = balanceItems;
	}

	public List<AccountBalanceFreezeItem> getBalanceFreezeItems() {
		return balanceFreezeItems;
	}

	public void setBalanceFreezeItems(List<AccountBalanceFreezeItem> balanceFreezeItems) {
		this.balanceFreezeItems = balanceFreezeItems;
	}

	public List<AccountBalanceLogItem> getBalanceLogItems() {
		return balanceLogItems;
	}

	public void setBalanceLogItems(List<AccountBalanceLogItem> balanceLogItems) {
		this.balanceLogItems = balanceLogItems;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

}
