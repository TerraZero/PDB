package tz.pdb.drivers.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import tz.core.logger.Log;
import tz.pdb.api.DBInsert;
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
	
	private Connection connection;

	/* 
	 * @see tz.pdb.api.driver.DBDriver#connect(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean connect(String host, String user, String pass) {
		Properties prop = null;
		if (user != null) {
			prop = new Properties();
			prop.put("user", user);
			if (pass != null) {
				prop.put("password", pass);
			}
		}
		try {
			if (prop == null) {
				this.connection = DriverManager.getConnection("jdbc:sqlite:" + host);
			} else {
				this.connection = DriverManager.getConnection("jdbc:sqlite:" + host, prop);
			}
		} catch (Exception e) {
			Log.critical(this.ident(), "Can not connect to host [0] with the user [1]", host, user);
			return false;
		}
		Log.success(this.ident(), "Connect to database via host [0] and user [1] successfully", host, user);
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

	@Override
	public DBSelect select(String from, String alias) {
		DBSelect select = new SQLiteSelect(from, alias);
		select.driver(this);
		return select;
	}

	@Override
	public DBTable table(String table) {
		DBTable create = new SQLiteTable(table);
		create.driver(this);
		return create;
	}

	@Override
	public DBInsert insert() {
		DBInsert insert = new SQLiteInsert();
		insert.driver(this);
		return insert;
	}

	@Override
	public DBInsert insert(String table) {
		DBInsert insert = new SQLiteInsert(table);
		insert.driver(this);
		return insert;
	}

	@Override
	public Statement execute() {
		try {
			return this.connection.createStatement();
		} catch (SQLException e) {
			Log.critical(this.ident(), "Can not create a SQLite statement interface");
			return null;
		}
	}
	
}
