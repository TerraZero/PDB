package tz.pdb.api.statements;

import tz.pdb.api.base.DBUpdating;
import tz.pdb.api.fields.DBCondition;

/**
 * 
 * @author terrazero
 * @created May 13, 2015
 * 
 * @file DBDelete.java
 * @project PDB
 * @identifier tz.pdb.api
 *
 */
public interface DBDelete extends DBUpdating {
	
	public static final String TYPE = "delete";
	
	public default String type() {
		return DBDelete.TYPE;
	}

	public String table();
	
	public DBDelete table(String table);
	
	public default DBCondition where(String one, String two) {
		return this.where(one, two, DBCondition.EQUAL);
	}
	
	public DBCondition where(String one, String two, String equal);
	
}
