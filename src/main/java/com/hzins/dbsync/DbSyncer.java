package com.hzins.dbsync;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DbSyncer {
	private int poolSize;
	private DataSource sourceDs;
	private DataSource targetDs;
	private ExecutorService executorService;
	private BlockingQueue<JobDef> taskDefQueue = new LinkedBlockingQueue<JobDef>();

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
		int minId = this.determineMixId();
		int maxId = this.determineMaxId();
		for (long i = minId; i <= maxId; i += step) {
			long beginIndex = i;
			long endIndex = (i == maxId) ? i + 1 : i + step;
			// put the range to a task
			taskDefQueue.put(new JobDef(beginIndex, endIndex));
		}

		long beginTime = System.currentTimeMillis();
		JobStat jobStat = new JobStat();
		// launch the job
		for (int i = 0; i < this.poolSize; i++) {
			this.executorService.execute(new SyncJob(this.sourceDs, this.targetDs, this.taskDefQueue, jobStat));
		}
		// stop new task
		this.executorService.shutdown();

		// print report
		this.showReport(jobStat, beginTime);
	}

	private void showReport(JobStat jobStat, long beginTime) throws Exception {
		while (true) {
			ConsoleLogger.info("Account created: " + jobStat.getWriteAccountCount().get());
			ConsoleLogger.info("Account Balance created: " + jobStat.getWriteBalanceCount().get());
			ConsoleLogger.info("Account Fund Log created: " + jobStat.getWriteFundLogCount().get());
			Thread.sleep(3 * 1000);
			if (this.executorService.isTerminated()) {
				ConsoleLogger.info("Account read: " + jobStat.getReadAccountCount().get());
				ConsoleLogger.info("Account Balance read: " + jobStat.getReadBalanceCount().get());
				ConsoleLogger.info("Account Fund Log read: " + jobStat.getReadFundLogCount().get());
				ConsoleLogger.info("Account created: " + jobStat.getWriteAccountCount().get());
				ConsoleLogger.info("Account Balance created: " + jobStat.getWriteBalanceCount().get());
				ConsoleLogger.info("Account Fund Log created: " + jobStat.getWriteFundLogCount().get());
				ConsoleLogger.info("Total elapsed time : " + (System.currentTimeMillis() - beginTime)/1000.0 + " seconds");
				ConsoleLogger.info("---------- Job Done! ----------");
				System.exit(0);
			}
		}
	}

	private int determineMaxId() {
		return 424326;
	}

	private int determineMixId() {
		return 0;
	}

	public static void main(String[] args) throws Exception {
		DbSyncer syncer = new DbSyncer(10);
		syncer.sync(100);
	}
}
