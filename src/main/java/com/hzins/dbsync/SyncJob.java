package com.hzins.dbsync;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import com.hzins.dbsync.impl.AccountExtractor;
import com.hzins.dbsync.impl.AccountLoader;
import com.hzins.dbsync.impl.domain.AccountMigration;

public class SyncJob implements Runnable {
	private DataSource sourceDs;
	private DataSource targetDs;
	private BlockingQueue<JobDef> jobQueue;
	private JobStat jobStat;

	public SyncJob(DataSource sourceDs, DataSource targetDs, BlockingQueue<JobDef> jobQueue, JobStat jobStat) {
		super();
		this.sourceDs = sourceDs;
		this.targetDs = targetDs;
		this.jobQueue = jobQueue;
		this.jobStat = jobStat;
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (this.jobQueue.size() <= 0) {
					return;
				}
				JobDef jobDef = this.jobQueue.poll(5, TimeUnit.SECONDS);
				if (jobDef == null) {
					return;
				}
				ConsoleLogger.debug("Prepare to handle job: " + jobDef.getBeginId() + "-" + jobDef.getEndId());

				// do database sync
				Map<Long, AccountMigration> accountMigMap = extractAndTranslateSource(jobDef);
				this.loadToTarget(accountMigMap, jobDef);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected final void loadToTarget(Map<Long, AccountMigration> accountMigMap, JobDef jobDef) throws SQLException {
		// load to target data source
		Connection targetConn = this.targetDs.getConnection();
		try {
			// need transaction, as we will operate database in batch mode
			targetConn.setAutoCommit(false);
			this.loadTarget(targetConn, accountMigMap);
			targetConn.commit();
		} catch (Exception e) {
			targetConn.rollback();
			ConsoleLogger.warn("Fail to load accounts with id between " + jobDef.getBeginId() + " and " + jobDef.getEndId());
			throw new RuntimeException(e);
		} finally {
			if (targetConn != null) {
				targetConn.close();
			}
		}
	}

	protected final Map<Long, AccountMigration> extractAndTranslateSource(JobDef jobDef) throws SQLException {
		// extract and translate data from source
		Connection sourceConn = this.sourceDs.getConnection();
		try {
			return this.extractSource(sourceConn, jobDef);
		} catch (Exception e) {
			throw new RuntimeException(e);

		} finally {
			if (sourceConn != null) {
				sourceConn.close();
			}
		}
	}

	protected void loadTarget(Connection conn, Map<Long, AccountMigration> accountMigMap) throws SQLException {
		AccountLoader loader = new AccountLoader();
		loader.loadTarget(conn, accountMigMap, jobStat);
	}

	protected Map<Long, AccountMigration> extractSource(Connection sourceConn, JobDef jobDef) throws SQLException {
		AccountExtractor extractor = new AccountExtractor();
		return extractor.extract(sourceConn, jobDef, jobStat);
	}

}
