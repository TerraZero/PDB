package tz.pdb.drivers.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
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
import tz.sys.SysUtil;

/**
 * 
 * @author terrazero
 * @created Apr 8, 2015
 * 
 * @file MysqlDriver.java
 * @project SysSQL
 * @identifier TZ.sql.driver.mysql
 *
 */
public class MysqlDriver implements DBDriver {
	
	private Connection connection;

	@Override
	public boolean connect(String host, String user, String pass) {
		try {
			this.connection = DriverManager.getConnection("jdbc:mysql://" + host + "?user=" + user + "&password=" + pass);
			SysUtil.log("Connect to database via host [0] and user [1] successfully!", host, user);
		} catch (Exception e) {
			SysUtil.error("Can not connect to host [0] with the user [1]!", host, user);
			return false;
		}
		return true;
	}

	@Override
	public DBSelect select() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBSelect select(String from, String alias) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBCreate create() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBCreate create(String table) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBInsert insert() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBInsert insert(String table) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement execute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBUpdate update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBUpdate update(String table) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBDelete delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBDelete delete(String table) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBOperation operation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBQuery query() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBQuery query(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBInfo info() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBDefineField defineField() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBResult execute(String type, String statement) {
		// TODO Auto-generated method stub
		return null;
	}

}
