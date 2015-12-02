package tz.io.pdb.api.fields;

import tz.io.pdb.api.base.DBBuilt;
import tz.io.pdb.api.base.DBChain;

/**
 * 
 * @author terrazero
 * @created Apr 8, 2015
 * 
 * @file SysCondition.java
 * @project SysSQL
 * @identifier TZ.sql.api
 *
 */
public interface DBCondition extends DBBuilt, DBChain<DBCondition> {
	
	public static final String EQUAL = "=";
	public static final String OP_AND = "AND";
	public static final String OP_OR = "OR";
	
	public default DBCondition and(String one, String two) {
		return this.and(one, two, DBCondition.EQUAL);
	}
	
	public DBCondition and(String one, String two, String equal);
	
	public default DBCondition or(String one, String two) {
		return this.or(one, two, DBCondition.EQUAL);
	}
	
	public DBCondition or(String one, String two, String equal);
	
}
