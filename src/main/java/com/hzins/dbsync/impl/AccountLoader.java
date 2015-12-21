package com.hzins.dbsync.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.hzins.dbsync.ConsoleLogger;
import com.hzins.dbsync.JobStat;
import com.hzins.dbsync.impl.constant.AccountStatusEnum;
import com.hzins.dbsync.impl.domain.AccountBalanceItem;
import com.hzins.dbsync.impl.domain.AccountBalanceLogItem;
import com.hzins.dbsync.impl.domain.AccountCreateItem;
import com.hzins.dbsync.impl.domain.AccountMigration;

public class AccountLoader {
	private int batchSize = 200;
	private static final String DEFAULT_RANDOM_KEY = "05e4f140f6bf35b0b836cbce3ae476a1";

	public void loadTarget(Connection conn, Map<Long, AccountMigration> accountMigMap, JobStat jobStat) throws SQLException {
		Collection<AccountMigration> accountMigs = accountMigMap.values();
		// insert account
		this.syncAccount(conn, accountMigs, jobStat);
		// insert account balance
		this.syncAccountBalance(conn, accountMigs, jobStat);
		// insert account balance logs
		this.syncAccountFundLogs(conn, accountMigs, jobStat);
	}

	private void syncAccountFundLogs(Connection conn, Collection<AccountMigration> accountMigs, JobStat jobStat) throws SQLException {
		String sql = "insert into account_fund_operation_record(account_id, source_type,currency_id,currency_value, cny_amount_value,before_value,after_value,"
		        + "operation_type,income_expenditure_type,remark,create_time,update_time) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		ConsoleLogger.debug("SQL: " + sql);
		PreparedStatement insertStat = conn.prepareStatement(sql);
		int count = 0;
		for (AccountMigration accountMig : accountMigs) {
			for (AccountBalanceLogItem fundLog : accountMig.getBalanceLogItems()) {
				insertStat.setLong(1, accountMig.getAccountId());
				insertStat.setInt(2, fundLog.getFromType().intValue());
				insertStat.setInt(3, fundLog.getCurrencyId().intValue());
				insertStat.setBigDecimal(4, fundLog.getAmount());
				insertStat.setLong(5, fundLog.getCnyAmount().longValue());
				insertStat.setBigDecimal(6, fundLog.getBalanceBeforeTrans());
				insertStat.setBigDecimal(7, fundLog.getBalanceAfterTrans());
				insertStat.setInt(8, fundLog.getOperationType().intValue());
				insertStat.setInt(9, fundLog.getIeType().intValue());
				insertStat.setString(10, fundLog.getRemark());
				insertStat.setTimestamp(11, new Timestamp(fundLog.getCreateTime().getTime()));
				insertStat.setTimestamp(12, new Timestamp(fundLog.getUpdateTime().getTime()));
				insertStat.addBatch();
				count++;
			}
			if (count % batchSize == 0) {
				int[] rows = insertStat.executeBatch();
				jobStat.getWriteFundLogCount().addAndGet(this.totalCount(rows));
			}
		}
		int[] rows = insertStat.executeBatch();
		jobStat.getWriteFundLogCount().addAndGet(this.totalCount(rows));
		insertStat.close();
	}

	private void syncAccountBalance(Connection conn, Collection<AccountMigration> accountMigs, JobStat jobStat) throws SQLException {
		String sql = "insert into account_balance(account_id, currency_id,amount, cny_amount,create_time,update_time) values(?,?,?,?,?,?)";
		ConsoleLogger.debug("SQL: " + sql);
		PreparedStatement insertStat = conn.prepareStatement(sql);
		int count = 0;
		for (AccountMigration accountMig : accountMigs) {
			for (AccountBalanceItem balanceItem : accountMig.getBalanceItems()) {
				insertStat.setLong(1, accountMig.getAccountId());
				insertStat.setInt(2, balanceItem.getCurrencyId().intValue());
				insertStat.setBigDecimal(3, balanceItem.getAmount());
				insertStat.setLong(4, balanceItem.getCnyAmount().longValue());
				insertStat.setTimestamp(5, new Timestamp(balanceItem.getCreateTime().getTime()));
				insertStat.setTimestamp(6, new Timestamp(balanceItem.getUpdateTime().getTime()));
				insertStat.addBatch();
				count++;
			}
			if (count % batchSize == 0) {
				int[] rows = insertStat.executeBatch();
				jobStat.getWriteBalanceCount().addAndGet(this.totalCount(rows));
			}
		}
		int[] rows = insertStat.executeBatch();
		jobStat.getWriteBalanceCount().addAndGet(this.totalCount(rows));

		insertStat.close();
	}

	private void syncAccount(Connection conn, Collection<AccountMigration> accountMigs,JobStat jobStat) throws SQLException {
		String sql = "insert into account(user_id, user_name,source_type, account_type,status,rand_key, create_time,update_time,account_id) values(?,?,?,?,?,?,?,?,?)";
		ConsoleLogger.debug("SQL: " + sql);
//		PreparedStatement batchInsert = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		PreparedStatement batchInsert = conn.prepareStatement(sql);
		int count = 0;
		for (AccountMigration accountMig : accountMigs) {
			AccountCreateItem createItem = accountMig.getAccountCreateItem();
			batchInsert.setLong(1, createItem.getUserId());
			batchInsert.setString(2, createItem.getUserName());
			batchInsert.setInt(3, createItem.getFromType().intValue());
			batchInsert.setInt(4, createItem.getAccountType().intValue());
			batchInsert.setInt(5, AccountStatusEnum.NORMAL.getValue());
			batchInsert.setString(6, DEFAULT_RANDOM_KEY);
			batchInsert.setTimestamp(7, new Timestamp(createItem.getCreateTime().getTime()));
			batchInsert.setTimestamp(8, new Timestamp(createItem.getUpdateTime().getTime()));
			batchInsert.setLong(9, accountMig.getAccountId());
			batchInsert.addBatch();
			count++;
			// jobStat.getWriteAccountCount().addAndGet(count);

			// ResultSet rs = batchInsert.getGeneratedKeys();
			// while (rs.next()) {
			// long id = rs.getLong(1);
			// accountMig.setAccountId(id);
			// // ConsoleLogger.debug("id:" + id);
			// }
			if (count % batchSize == 0) {
				int[] rows = batchInsert.executeBatch();
				jobStat.getWriteAccountCount().addAndGet(this.totalCount(rows));
			}
		}
		int[] rows = batchInsert.executeBatch();
		jobStat.getWriteAccountCount().addAndGet(this.totalCount(rows));

		batchInsert.close();
	}

	private int totalCount(int[] counts) {
		int total = 0;
		for (int count : counts) {
			total += count;
		}
		return total;
	}
}
