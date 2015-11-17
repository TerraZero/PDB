package tz.pdb.drivers.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import tz.pdb.api.DBDriver;
import tz.pdb.api.fields.DBDefineField;
import tz.pdb.api.functions.DBResult;
import tz.pdb.api.statements.DBCreate;
import tz.pdb.api.statements.DBDelete;
import tz.pdb.api.statements.DBInfo;
import tz.pdb.api.statements.DBInsert;
import tz.pdb.api.statements.DBOperation;
import tz.pdb.api.statements.DBQuery;
import tz.pdb.api.statements.DBSelect;
import tz.pdb.api.statements.DBUpdate;
import tz.pdb.drivers.sqlite.fields.SQLiteDefineField;
import tz.pdb.drivers.sqlite.statements.SQLiteDelete;
import tz.pdb.drivers.sqlite.statements.SQLiteInfo;
import tz.pdb.drivers.sqlite.statements.SQLiteInsert;
import tz.pdb.drivers.sqlite.statements.SQLiteOperation;
import tz.pdb.drivers.sqlite.statements.SQLiteQuery;
import tz.pdb.drivers.sqlite.statements.SQLiteSelect;
import tz.pdb.drivers.sqlite.statements.SQLiteCreate;
import tz.pdb.drivers.sqlite.statements.SQLiteUpdate;
import tz.sys.SysUtil;

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
	private DBInfo info;

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
				SysUtil.log("Connect to database via host [0] successfully", host);
			} else {
				this.connection = DriverManager.getConnection("jdbc:sqlite:" + host, prop);
				SysUtil.log("Connect to database via host [0] and user [1] successfully!", host, user);
			}
		} catch (Exception e) {
			SysUtil.error("Can not connect to host [0] with the user [1]!", host, user);
			return false;
		}
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
		return "DB::Driver::SQLite";
	}

	/* 
	 * @see tz.pdb.api.driver.DBDriver#table()
	 */
	@Override
	public DBCreate create() {
		DBCreate table = new SQLiteCreate();
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
	public DBCreate create(String table) {
		DBCreate create = new SQLiteCreate(table);
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
			SysUtil.error("Can not create a SQLite statement interface!");
			return null;
		}
	}

	/* 
	 * @see tz.pdb.api.driver.DBDriver#update()
	 */
	@Override
	public DBUpdate update() {
		DBUpdate update = new SQLiteUpdate();
		update.driver(this);
		return update;
	}

	/* 
	 * @see tz.pdb.api.driver.DBDriver#update(java.lang.String)
	 */
	@Override
	public DBUpdate update(String table) {
		DBUpdate update = new SQLiteUpdate(table);
		update.driver(this);
		return update;
	}

	/* 
	 * @see tz.pdb.api.driver.DBDriver#delete()
	 */
	@Override
	public DBDelete delete() {
		DBDelete delete = new SQLiteDelete();
		delete.driver(this);
		return delete;
	}

	/* 
	 * @see tz.pdb.api.driver.DBDriver#delete(java.lang.String)
	 */
	@Override
	public DBDelete delete(String table) {
		DBDelete delete = new SQLiteDelete(table);
		delete.driver(this);
		return delete;
	}

	@Override
	public DBOperation operation() {
		DBOperation operation = new SQLiteOperation();
		operation.driver(this);
		return operation;
	}

	@Override
	public DBQuery query() {
		DBQuery query = new SQLiteQuery();
		query.driver(this);
		return query;
	}

	@Override
	public DBQuery query(String query) {
		DBQuery dbquery = new SQLiteQuery(query);
		dbquery.driver(this);
		return dbquery;
	}

	@Override
	public DBInfo info() {
		if (this.info == null) {
			this.info = new SQLiteInfo();
			this.info.driver(this);
		}
		return this.info;
	}

	@Override
	public DBDefineField defineField() {
		return new SQLiteDefineField();
	}

	@Override
	public DBResult execute(String type, String statement) {
		try {
			switch (type) {
				case DBSelect.TYPE :
				case DBQuery.TYPE :
					return new DBResult(statement, type, this.execute().executeQuery(statement));
				case DBUpdate.TYPE :
				case DBDelete.TYPE :
				case DBCreate.TYPE :
					return new DBResult(statement, type, this.execute().executeUpdate(statement));
				default :
					SysUtil.warn("The statment type [0] is not supported.", type);
			}
		} catch (SQLException e) {
			return new DBResult(statement, type, e);
		}
		return null;
	}
	
}
