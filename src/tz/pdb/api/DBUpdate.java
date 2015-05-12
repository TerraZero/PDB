package tz.pdb.api;

import tz.pdb.api.base.DBUpdating;
import tz.pdb.api.statments.DBCondition;

/**
 * 
 * @author terrazero
 * @created May 12, 2015
 * 
 * @file DBUpdate.java
 * @project PDB
 * @identifier tz.pdb.api
 *
 */
public interface DBUpdate extends DBUpdating {

	public String table();
	
	public DBUpdate table(String table);
	
	public DBUpdate set(String col, String value);
	
	public default DBCondition where(String one, String two) {
		return this.where(one, two, DBCondition.EQUAL);
	}
	
	public DBCondition where(String one, String two, String equal);
	
}
