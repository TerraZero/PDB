package tz.pdb.drivers.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import tz.core.logger.Log;
import tz.pdb.api.DBSelect;
import tz.pdb.api.DBTable;
import tz.pdb.api.driver.DBDriver;

/**
 * 
 * @author terrazero
 * @created May 2, 2015
 * 
 * @file SQLiteDriver.java
 * @project SysSQL
 * @identifier TZ.sql.driver.sqlite
 *
 */
public class SQLiteDriver implements DBDriver {

	public static void main(String[] args) {
		Connection c = null;
	    try {
	      c = DriverManager.getConnection("jdbc:sqlite:db/my.db");
	      Statement s = c.createStatement();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Opened database successfully");
	}
	
	private Connection connection;

	/* 
	 * @see tz.pdb.api.driver.DBDriver#connect(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean connect(String host, String user, String pass) {
		if (user != null && user.length() != 0) Log.warning(this.ident(), "SQLite does not react to given user [0]", user);
		if (pass!= null && pass.length() != 0) Log.warning(this.ident(), "SQLite does not react to given pass ***");
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlite:" + host);
		} catch (Exception e) {
			Log.critical(this.ident(), "Can not connect to host [0]", host);
			return false;
		}
		Log.success(this.ident(), "Connect to database via host [0] successfully", host);
		return true;
	}

	/* 
	 * @see tz.pdb.api.driver.DBDriver#select()
	 */
	@Override
	public DBSelect select() {
		DBSelect select = new SQLiteSelect();
		select.driver(this);
		return select;
	}
	
	public String ident() {
		return Log.ident("DB", "Driver", "SQLite");
	}

	/* 
	 * @see tz.pdb.api.driver.DBDriver#table()
	 */
	@Override
	public DBTable table() {
		DBTable table = new SQLiteTable();
		table.driver(this);
		return table;
	}
	
}
