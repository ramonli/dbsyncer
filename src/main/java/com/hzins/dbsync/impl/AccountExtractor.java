package com.hzins.dbsync.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hzins.dbsync.ConsoleLogger;
import com.hzins.dbsync.JobDef;
import com.hzins.dbsync.JobStat;
import com.hzins.dbsync.impl.constant.AccountFromTypeEnum;
import com.hzins.dbsync.impl.constant.CurrencyEnum;
import com.hzins.dbsync.impl.constant.IncomeExpenditureTypeEnum;
import com.hzins.dbsync.impl.domain.AccountBalanceItem;
import com.hzins.dbsync.impl.domain.AccountBalanceLogItem;
import com.hzins.dbsync.impl.domain.AccountCreateItem;
import com.hzins.dbsync.impl.domain.AccountMigration;

public class AccountExtractor {

	public Map<Long, AccountMigration> extract(Connection sourceConn, JobDef jobDef, JobStat jobStat) throws SQLException {
		Map<Long, AccountMigration> accountMigMap = new HashMap<Long, AccountMigration>();
		// lookup account
		this.lookupAccount(sourceConn, jobDef, accountMigMap, jobStat);
		if (accountMigMap.size() > 0) {
			this.lookupFundLogs(sourceConn, jobDef, accountMigMap, jobStat);
		}
		return accountMigMap;
	}

	private void lookupFundLogs(Connection sourceConn, JobDef jobDef, Map<Long, AccountMigration> accountMigMap, JobStat jobStat) throws SQLException {
		String userIds = this.assembleIds(accountMigMap);
		Statement statement = sourceConn.createStatement();
		// 余额日志
		String sql = "select * from AccountMoneyLog where OrderNum='' and UserId in (" + userIds + ") and Deleted = 0";
		ConsoleLogger.debug("SQL: " + sql);
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
			AccountBalanceLogItem log = new AccountBalanceLogItem();
			log.setUserId(rs.getLong("UserId"));
			log.setFromType((int) AccountFromTypeEnum.HZ.getValue());
			log.setCurrencyId(CurrencyEnum.BALANCE.getId());
			BigDecimal moneyUse = rs.getBigDecimal("MoneyUse");
			BigDecimal moneyIn = rs.getBigDecimal("MoneyIn");
			if (moneyUse.compareTo(moneyIn) == 1) {
				// 支出
				log.setAmount(moneyUse.multiply(new BigDecimal("100")));
				log.setIeType((int) IncomeExpenditureTypeEnum.EXPENDITURE.getValue());
			} else {
				log.setAmount(moneyIn.multiply(new BigDecimal("100")));
				log.setIeType((int) IncomeExpenditureTypeEnum.INCOME.getValue());
			}
			log.setCnyAmount(log.getAmount().longValue());
			log.setBalanceAfterTrans(rs.getBigDecimal("Balance").multiply(new BigDecimal("100")));
			log.setOperationType(rs.getInt("TypeId"));
			log.setRemark(rs.getString("Contents"));
			log.setCreateTime(rs.getTimestamp("CreateTime"));
			log.setUpdateTime(log.getCreateTime());

			accountMigMap.get(log.getUserId()).getBalanceLogItems().add(log);
			jobStat.getReadFundLogCount().incrementAndGet();
		}

		// 金豆日志
		sql = "select * from AccountGoldLog where OrderNum='' and UserId in (" + userIds + ") and Deleted = 0";
		ConsoleLogger.debug("SQL: " + sql);
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			AccountBalanceLogItem log = new AccountBalanceLogItem();
			log.setUserId(rs.getLong("UserId"));
			log.setFromType((int) AccountFromTypeEnum.HZ.getValue());
			log.setCurrencyId(CurrencyEnum.BALANCE.getId());
			BigDecimal moneyUse = rs.getBigDecimal("GoldUse");
			BigDecimal moneyIn = rs.getBigDecimal("GoldIn");
			if (moneyUse.compareTo(moneyIn) == 1) {
				// 支出
				log.setAmount(moneyUse);
				log.setIeType((int) IncomeExpenditureTypeEnum.EXPENDITURE.getValue());
			} else {
				log.setAmount(moneyIn);
				log.setIeType((int) IncomeExpenditureTypeEnum.INCOME.getValue());
			}
			log.setCnyAmount(log.getAmount().multiply(new BigDecimal("10")).longValue());
			log.setBalanceAfterTrans(rs.getBigDecimal("Balance"));
			log.setOperationType(rs.getInt("TypeId"));
			log.setRemark(rs.getString("Contents"));
			log.setCreateTime(rs.getTimestamp("CreateTime"));
			log.setUpdateTime(log.getCreateTime());

			accountMigMap.get(log.getUserId()).getBalanceLogItems().add(log);
			jobStat.getReadFundLogCount().incrementAndGet();
		}

		rs.close();
		statement.close();
	}

	private void lookupAccount(Connection sourceConn, JobDef jobDef, Map<Long, AccountMigration> accountMigMap, JobStat jobStat) throws SQLException {
		long lastUserId = -999;
		Statement statement = sourceConn.createStatement();
		String sql = "select * from Account where UserId>=" + jobDef.getBeginId() + " and UserId<" + jobDef.getEndId() + " and Deleted = 0  order by UserId";
		ResultSet rs = statement.executeQuery(sql);
		ConsoleLogger.debug("SQL: " + sql);
		while (rs.next()) {
			// filter duplicated userId...we have order the result by userID
			long userId = rs.getLong("UserId");
			if (userId == lastUserId) {
				ConsoleLogger.warn("[WARN] Dulplicated userId " + userId + ", only one will be synced.");
				jobStat.getDuplicatedAccountCount().incrementAndGet();
				continue;
			}
			lastUserId = userId;
			AccountMigration accountMig = new AccountMigration();
			AccountCreateItem createItem = new AccountCreateItem();
			createItem.setUserName(rs.getString("UserName"));
			createItem.setUserId(userId);
			createItem.setAccountType((byte) 1);
			createItem.setCreateTime(rs.getTimestamp("CreateTime"));
			createItem.setUpdateTime(rs.getTimestamp("UpdateTime"));
			createItem.setFromType(AccountFromTypeEnum.HZ.getValue());
			accountMig.setAccountCreateItem(createItem);
			jobStat.getReadAccountCount().incrementAndGet();

			List<AccountBalanceItem> balanceItems = new LinkedList<AccountBalanceItem>();
			// 余额
			AccountBalanceItem balanceItem = new AccountBalanceItem();
			balanceItem.setCurrencyId(CurrencyEnum.BALANCE.getId());
			balanceItem.setAmount(rs.getBigDecimal("Balance").multiply(new BigDecimal("100")));
			balanceItem.setCnyAmount(balanceItem.getAmount().longValue());
			balanceItem.setCreateTime(createItem.getCreateTime());
			balanceItem.setUpdateTime(createItem.getUpdateTime());
			balanceItems.add(balanceItem);
			// 金豆
			AccountBalanceItem goldbeanItem = new AccountBalanceItem();
			goldbeanItem.setCurrencyId(CurrencyEnum.GOLDEN_BEAN.getId());
			goldbeanItem.setAmount(rs.getBigDecimal("Gold"));
			goldbeanItem.setCnyAmount(goldbeanItem.getAmount().multiply(new BigDecimal("10")).longValue());
			goldbeanItem.setCreateTime(createItem.getCreateTime());
			goldbeanItem.setUpdateTime(createItem.getUpdateTime());
			balanceItems.add(goldbeanItem);

			accountMig.setBalanceItems(balanceItems);
			accountMigMap.put(createItem.getUserId(), accountMig);
			jobStat.getWriteBalanceCount().addAndGet(2);
		}

		rs.close();
		statement.close();
	}

	private String assembleIds(Map<Long, AccountMigration> accountMigMap) {
		Iterator<Long> ite = accountMigMap.keySet().iterator();
		StringBuffer sb = new StringBuffer("");
		while (ite.hasNext()) {
			sb.append(ite.next()).append(",");
		}
		String ids = sb.toString();
		if (accountMigMap.size() > 0) {
			ids = ids.substring(0, ids.length() - 1);
		}
		return ids;
	}
}
