package tz.io.pdb.drivers.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import tz.io.pdb.api.DBDriver;
import tz.io.pdb.api.fields.DBDefineField;
import tz.io.pdb.api.functions.DBResult;
import tz.io.pdb.api.statements.DBCreate;
import tz.io.pdb.api.statements.DBDelete;
import tz.io.pdb.api.statements.DBInfo;
import tz.io.pdb.api.statements.DBInsert;
import tz.io.pdb.api.statements.DBOperation;
import tz.io.pdb.api.statements.DBQuery;
import tz.io.pdb.api.statements.DBSelect;
import tz.io.pdb.api.statements.DBUpdate;
import tz.io.pdb.drivers.sql.fields.SQLDefineField;
import tz.io.pdb.drivers.sql.statements.SQLCreate;
import tz.io.pdb.drivers.sql.statements.SQLDelete;
import tz.io.pdb.drivers.sql.statements.SQLInfo;
import tz.io.pdb.drivers.sql.statements.SQLInsert;
import tz.io.pdb.drivers.sql.statements.SQLOperation;
import tz.io.pdb.drivers.sql.statements.SQLQuery;
import tz.io.pdb.drivers.sql.statements.SQLSelect;
import tz.io.pdb.drivers.sql.statements.SQLUpdate;
import tz.sys.Sys;

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
public abstract class SQLDriver implements DBDriver {
	
	protected Connection connection;
	protected DBInfo info;

	/* 
	 * @see tz.pdb.api.driver.DBDriver#connect(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean connect(String host, String user, String pass) {
		Sys.error("Default connection not implement.");
		return false;
	}

	/* 
	 * @see tz.pdb.api.driver.DBDriver#select()
	 */
	@Override
	public DBSelect select() {
		DBSelect select = new SQLSelect();
		select.driver(this);
		return select;
	}
	
	public String ident() {
		return "DB::Driver::Def";
	}

	/* 
	 * @see tz.pdb.api.driver.DBDriver#table()
	 */
	@Override
	public DBCreate create() {
		DBCreate table = new SQLCreate();
		table.driver(this);
		return table;
	}

	@Override
	public DBSelect select(String from, String alias) {
		DBSelect select = new SQLSelect(from, alias);
		select.driver(this);
		return select;
	}

	@Override
	public DBCreate create(String table) {
		DBCreate create = new SQLCreate(table);
		create.driver(this);
		return create;
	}

	@Override
	public DBInsert insert() {
		DBInsert insert = new SQLInsert();
		insert.driver(this);
		return insert;
	}

	@Override
	public DBInsert insert(String table) {
		DBInsert insert = new SQLInsert(table);
		insert.driver(this);
		return insert;
	}

	@Override
	public Statement execute() {
		try {
			return this.connection.createStatement();
		} catch (SQLException e) {
			Sys.error("Can not create a SQLite statement interface!");
			return null;
		}
	}

	/* 
	 * @see tz.pdb.api.driver.DBDriver#update()
	 */
	@Override
	public DBUpdate update() {
		DBUpdate update = new SQLUpdate();
		update.driver(this);
		return update;
	}

	/* 
	 * @see tz.pdb.api.driver.DBDriver#update(java.lang.String)
	 */
	@Override
	public DBUpdate update(String table) {
		DBUpdate update = new SQLUpdate(table);
		update.driver(this);
		return update;
	}

	/* 
	 * @see tz.pdb.api.driver.DBDriver#delete()
	 */
	@Override
	public DBDelete delete() {
		DBDelete delete = new SQLDelete();
		delete.driver(this);
		return delete;
	}

	/* 
	 * @see tz.pdb.api.driver.DBDriver#delete(java.lang.String)
	 */
	@Override
	public DBDelete delete(String table) {
		DBDelete delete = new SQLDelete(table);
		delete.driver(this);
		return delete;
	}

	@Override
	public DBOperation operation() {
		DBOperation operation = new SQLOperation();
		operation.driver(this);
		return operation;
	}

	@Override
	public DBQuery query() {
		DBQuery query = new SQLQuery();
		query.driver(this);
		return query;
	}

	@Override
	public DBQuery query(String query) {
		DBQuery dbquery = new SQLQuery(query);
		dbquery.driver(this);
		return dbquery;
	}

	@Override
	public DBInfo info() {
		if (this.info == null) {
			this.info = new SQLInfo();
			this.info.driver(this);
		}
		return this.info;
	}

	@Override
	public DBDefineField defineField() {
		return new SQLDefineField();
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
					Sys.warn("The statment type [0] is not supported.", type);
			}
		} catch (SQLException e) {
			return new DBResult(statement, type, e);
		}
		return null;
	}
	
}
