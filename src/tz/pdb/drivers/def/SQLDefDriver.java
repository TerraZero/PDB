package tz.pdb.drivers.def;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
import tz.pdb.drivers.def.fields.SQLDefDefineField;
import tz.pdb.drivers.def.statements.SQLDefCreate;
import tz.pdb.drivers.def.statements.SQLDefDelete;
import tz.pdb.drivers.def.statements.SQLDefInfo;
import tz.pdb.drivers.def.statements.SQLDefInsert;
import tz.pdb.drivers.def.statements.SQLDefOperation;
import tz.pdb.drivers.def.statements.SQLDefQuery;
import tz.pdb.drivers.def.statements.SQLDefSelect;
import tz.pdb.drivers.def.statements.SQLDefUpdate;
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
public class SQLDefDriver implements DBDriver {
	
	protected Connection connection;
	protected DBInfo info;

	/* 
	 * @see tz.pdb.api.driver.DBDriver#connect(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean connect(String host, String user, String pass) {
		SysUtil.error("Default connection not implement.");
		return false;
	}

	/* 
	 * @see tz.pdb.api.driver.DBDriver#select()
	 */
	@Override
	public DBSelect select() {
		DBSelect select = new SQLDefSelect();
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
		DBCreate table = new SQLDefCreate();
		table.driver(this);
		return table;
	}

	@Override
	public DBSelect select(String from, String alias) {
		DBSelect select = new SQLDefSelect(from, alias);
		select.driver(this);
		return select;
	}

	@Override
	public DBCreate create(String table) {
		DBCreate create = new SQLDefCreate(table);
		create.driver(this);
		return create;
	}

	@Override
	public DBInsert insert() {
		DBInsert insert = new SQLDefInsert();
		insert.driver(this);
		return insert;
	}

	@Override
	public DBInsert insert(String table) {
		DBInsert insert = new SQLDefInsert(table);
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
		DBUpdate update = new SQLDefUpdate();
		update.driver(this);
		return update;
	}

	/* 
	 * @see tz.pdb.api.driver.DBDriver#update(java.lang.String)
	 */
	@Override
	public DBUpdate update(String table) {
		DBUpdate update = new SQLDefUpdate(table);
		update.driver(this);
		return update;
	}

	/* 
	 * @see tz.pdb.api.driver.DBDriver#delete()
	 */
	@Override
	public DBDelete delete() {
		DBDelete delete = new SQLDefDelete();
		delete.driver(this);
		return delete;
	}

	/* 
	 * @see tz.pdb.api.driver.DBDriver#delete(java.lang.String)
	 */
	@Override
	public DBDelete delete(String table) {
		DBDelete delete = new SQLDefDelete(table);
		delete.driver(this);
		return delete;
	}

	@Override
	public DBOperation operation() {
		DBOperation operation = new SQLDefOperation();
		operation.driver(this);
		return operation;
	}

	@Override
	public DBQuery query() {
		DBQuery query = new SQLDefQuery();
		query.driver(this);
		return query;
	}

	@Override
	public DBQuery query(String query) {
		DBQuery dbquery = new SQLDefQuery(query);
		dbquery.driver(this);
		return dbquery;
	}

	@Override
	public DBInfo info() {
		if (this.info == null) {
			this.info = new SQLDefInfo();
			this.info.driver(this);
		}
		return this.info;
	}

	@Override
	public DBDefineField defineField() {
		return new SQLDefDefineField();
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
