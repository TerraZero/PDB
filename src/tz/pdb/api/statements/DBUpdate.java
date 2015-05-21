package tz.pdb.api.statements;

import tz.pdb.api.base.DBUpdating;
import tz.pdb.api.fields.DBCondition;

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
	
	public static final String TYPE = "update";
	
	public default String type() {
		return DBUpdate.TYPE;
	}

	public String table();
	
	public DBUpdate table(String table);
	
	public DBUpdate update(String col, String value);
	
	public DBUpdate set(String col, String value);
	
	public DBUpdate set(String col, int value);
	
	public default DBCondition where(String one, String two) {
		return this.where(one, two, DBCondition.EQUAL);
	}
	
	public DBCondition where(String one, String two, String equal);
	
}
