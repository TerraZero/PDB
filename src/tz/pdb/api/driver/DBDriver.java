package tz.pdb.api.driver;

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
	
	public DBTable table();
	
}
