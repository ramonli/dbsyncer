package com.hzins.dbsync.source.sqlserver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

public class SqlServerConnection {

	@Test
	public void test() throws Exception {
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动
		String dbURL = "jdbc:sqlserver://172.16.17.81:1433;DatabaseName=HzInsCore"; // 连接服务器和数据库sample
		String userName = "lushuobo"; // 默认用户名
		String userPwd = "lushuobo"; // 密码

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass(driverName); // loads the jdbc driver
		cpds.setJdbcUrl(dbURL);
		cpds.setUser(userName);
		cpds.setPassword(userPwd);
		cpds.setInitialPoolSize(5);
		cpds.setMinPoolSize(5);                                     
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);

		Connection dbConn;
		try {
//			Class.forName(driverName);
//			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
			dbConn = cpds.getConnection();
			Statement sql = dbConn.createStatement();
			ResultSet rs = sql.executeQuery("select UserName from Account where UserId=1");
			System.out.println("Connection Successful!"); // 如果连接成功 控制台输出Connection Successful!
			while (rs.next()) {
				System.out.println("<td>" + rs.getString(1) + "</td>");
			}

			dbConn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSources.destroy( cpds );
		}
	}

}
