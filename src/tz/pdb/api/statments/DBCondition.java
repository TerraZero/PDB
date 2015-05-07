package tz.pdb.api.statments;

import tz.pdb.api.DBVar;
import tz.pdb.api.base.DBChain;
import tz.pdb.api.base.DBExecute;

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
public interface DBCondition extends DBExecute, DBChain<DBCondition> {
	
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
	
	public DBCondition and(DBVar var);
	
}
