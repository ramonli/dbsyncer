package com.hzins.dbsync;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/*
 * Account read: 358616
 * Account Balance read: 717232
 * Account Balance Fund Log read: 3347
 * Account GoldBean Fund Log read: 165364
 */
public class DbSyncer {
	private int poolSize;
	private DataSource sourceDs;
	private DataSource targetDs;
	private ExecutorService executorService;
	private BlockingQueue<JobDef> taskDefQueue = new LinkedBlockingQueue<JobDef>();
	private AtomicLong ID = new AtomicLong(0);

	public DbSyncer(int poolSize) throws Exception {
		this.poolSize = poolSize;

		// initialization
		this.initDs();
		this.executorService = Executors.newFixedThreadPool(poolSize);
	}

	private void initDs() throws Exception {
		this.initSourceDs();
		this.initTargetDs();
	}

	private void initTargetDs() throws Exception {
		String driverName = "com.mysql.jdbc.Driver";
		String dbURL = "jdbc:mysql://localhost:3306/red_env";
		String userName = "root";
		String userPwd = "";
		// String dbURL = "jdbc:mysql://192.168.10.58:8066/AC";
		// String userName = "test";
		// String userPwd = "test";

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass(driverName); // loads the jdbc driver
		cpds.setJdbcUrl(dbURL);
		cpds.setUser(userName);
		cpds.setPassword(userPwd);
		cpds.setInitialPoolSize(this.poolSize);
		cpds.setMinPoolSize(this.poolSize);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(this.poolSize * 2);
		this.targetDs = cpds;

		ConsoleLogger.info("Init target datasource successfully.");
	}

	private void initSourceDs() throws Exception {
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动
		String dbURL = "jdbc:sqlserver://172.16.17.81:1433;DatabaseName=HzInsCore"; // 连接服务器和数据库sample
		String userName = "lushuobo"; // 默认用户名
		String userPwd = "lushuobo"; // 密码

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass(driverName); // loads the jdbc driver
		cpds.setJdbcUrl(dbURL);
		cpds.setUser(userName);
		cpds.setPassword(userPwd);
		cpds.setInitialPoolSize(this.poolSize);
		cpds.setMinPoolSize(this.poolSize);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(this.poolSize * 2);
		this.sourceDs = cpds;

		ConsoleLogger.info("Init source datasource successfully.");
	}

	public void sync(int step) throws Exception {
		long minId = this.determineMixId();
		long maxId = this.determineMaxId();
		if (maxId < minId) {
			ConsoleLogger.warn("maxId(" + maxId + ") can't be less than minId(" + minId + ")");
			return;
		}
		this.splitTask(taskDefQueue, step, minId, maxId);
		welcome(minId, maxId);

		long beginTime = System.currentTimeMillis();
		JobStat jobStat = new JobStat();
		// launch the job
		for (int i = 0; i < this.poolSize; i++) {
			this.executorService.execute(new SyncJob(this.sourceDs, this.targetDs, this.taskDefQueue, jobStat, ID));
		}
		// stop new task
		this.executorService.shutdown();

		// print report
		this.showReport(jobStat, beginTime);
	}

	protected void splitTask(BlockingQueue<JobDef> queue, int step, long minId, long maxId) throws InterruptedException {
		boolean reachEnd = false;
		for (long i = minId; i <= maxId; i += step) {
			long beginIndex = i;
			long endIndex = i + step;
			if (endIndex > maxId) {
				endIndex = maxId + 1;
				reachEnd = true;
			}
			// put the range to a task
			queue.put(new JobDef(beginIndex, endIndex));
			if (reachEnd) {
				break;
			}
		}
	}

	private void welcome(long minId, long maxId) {
		ConsoleLogger.info("Prepare to sync data between SqlServer and Mysql.");
		ConsoleLogger.info("Count of worker thread: " + this.poolSize);
		ConsoleLogger.info("minId of account: " + minId);
		ConsoleLogger.info("maxId of account: " + maxId);
		ConsoleLogger.info("--------------------------------");
	}

	private void showReport(JobStat jobStat, long beginTime) throws Exception {
		while (true) {
			Thread.sleep(5 * 1000);
			ConsoleLogger.info("Account created: " + jobStat.getWriteAccountCount().get());
			ConsoleLogger.info("Account Balance created: " + jobStat.getWriteBalanceCount().get());
			ConsoleLogger.info("Account Fund Log created: " + jobStat.getWriteFundLogCount().get());
			if (this.executorService.isTerminated()) {
				ConsoleLogger.info("--------------------------------");
				ConsoleLogger.info("Account read: " + jobStat.getReadAccountCount().get());
				ConsoleLogger.info("Account Balance read: " + jobStat.getReadBalanceCount().get());
				ConsoleLogger.info("Account Balance Fund Log read: " + jobStat.getReadBalanceFundLogCount().get());
				ConsoleLogger.info("Account GoldBean Fund Log read: " + jobStat.getReadGoldBeanFundLogCount().get());
				ConsoleLogger.info("Account created: " + jobStat.getWriteAccountCount().get());
				ConsoleLogger.info("Account Balance created: " + jobStat.getWriteBalanceCount().get());
				ConsoleLogger.info("Account Fund Log created: " + jobStat.getWriteFundLogCount().get());
				ConsoleLogger.info("Total elapsed time : " + (System.currentTimeMillis() - beginTime) / 1000.0 + " seconds");
				ConsoleLogger.info("--------------------------------");
				ConsoleLogger.info("Job Done! Remember to make the sequence of mycat be greater than max(id) of account table.");
				System.exit(0);
			}
		}
	}

	private long determineMaxId() throws Exception {
		// return 300000;
		long maxId = Long.MIN_VALUE;
		Connection conn = null;
		try {
			conn = this.sourceDs.getConnection();
			Statement statement = conn.createStatement();
			String sql = "select max(UserId) from Account";
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) {
				maxId = rs.getLong(1);
			}
			rs.close();
			statement.close();
		} catch (Exception e) {
			ConsoleLogger.warn(e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return maxId;
	}

	private int determineMixId() {
		return 0;
	}

	public static void main(String[] args) throws Exception {
		DbSyncer syncer = new DbSyncer(20);
		syncer.sync(300);
	}
}
