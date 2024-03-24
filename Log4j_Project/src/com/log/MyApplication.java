package com.log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

public class MyApplication {
	private static String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static String username = "system";
	private static String password = "System";

	private static Connection con = null;
	private static Statement stmt = null;
	private static ResultSet rs = null;

	private static Logger logger = Logger.getLogger(MyApplication.class);
	static {
		try {
			// create layout obj
			SimpleLayout layout = new SimpleLayout();
			// appender obj
			ConsoleAppender appender = new ConsoleAppender(layout);
			// add appender
			logger.addAppender(appender);
			// logger level to retrieve msgs
			
			logger.setLevel(Level.DEBUG);
			
			logger.info("Log4j Setup is ready");
		} // try
		catch (Exception e) {
			e.printStackTrace();
		} // catch
	}// static block

	public static void main(String[] args) {
		logger.debug("start of main method");
		try {
			con = DriverManager.getConnection(url, username, password);
			logger.info("connection obj is created");
			if (con != null) {
				stmt = con.createStatement();
				logger.info("statement obj is created");
			} // if
			if (stmt != null) {
				String sql = "select *from emp where job ='MANAGER'";
				rs = stmt.executeQuery(sql);
				logger.info("ResultSet obj is created");
			} // if
			if (rs != null) {
				while (rs.next()) {
					System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3));
					logger.info("Query is executed");
				} // while
			} // if
		} // try
		catch (SQLException sql) {
			sql.printStackTrace();
			logger.error("Some Problem in DB level, Please resolve the issue");
		} // catch
		catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e.getMessage());
		} // catch
		finally {
			try {
				if (con != null) {
					con.close();
					logger.debug("connection is closed");
				} // if
			} // try
			catch (Exception e) {
				e.printStackTrace();
				logger.fatal(e.getMessage());
			} // catch

			try {
				if (stmt != null) {
					stmt.close();
					logger.debug("statement is closed ");
				} // if
			} // try
			catch (Exception e) {
				e.printStackTrace();
				logger.fatal(e.getMessage());
			} // catch

			try {
				if (rs != null) {
					rs.close();
					logger.debug("ResultSet is closed");
				} // if
			} // try
			catch (Exception e) {
				e.printStackTrace();
				logger.fatal(e.getMessage());
			} // catch

		} // finally
		logger.debug("end of main method");
	}// main
}// class