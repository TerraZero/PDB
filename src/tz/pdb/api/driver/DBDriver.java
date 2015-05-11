package tz.pdb.api.driver;

import java.sql.Statement;

import tz.pdb.api.DBInsert;
import tz.pdb.api.DBSelect;
import tz.pdb.api.DBTable;

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
	
	public boolean connect(String host, String user, String pass);

	public DBSelect select();
	
	public DBSelect select(String from, String alias);
	
	public DBTable table();
	
	public DBTable table(String table);
	
	public DBInsert insert();
	
	public DBInsert insert(String table);
	
	public Statement execute();
	
}
