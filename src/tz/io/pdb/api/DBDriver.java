package tz.io.pdb.api;

import java.sql.Statement;

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

/**
 * 
 * @author terrazero
 * @created Apr 10, 2015
 * 
 * @file SysDriver.java
 * @project SysSQL
 * @identifier TZ.sql.api.driver
 *
 */
public interface DBDriver {
	
	public String name();
	
	public boolean connect(String host, String user, String pass);

	public DBSelect select();
	
	public DBSelect select(String from, String alias);
	
	public DBCreate create();
	
	public DBCreate create(String table);
	
	public DBInsert insert();
	
	public DBInsert insert(String table);
	
	public Statement execute();
	
	public DBUpdate update();
	
	public DBUpdate update(String table);
	
	public DBDelete delete();
	
	public DBDelete delete(String table);
	
	public DBOperation operation();
	
	public DBQuery query();
	
	public DBQuery query(String query);
	
	public DBInfo info();
	
	public DBDefineField defineField();
	
	public DBResult execute(String type, String statement);
	
}
