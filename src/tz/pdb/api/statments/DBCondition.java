package tz.pdb.api.statments;

import tz.pdb.SysVar;
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
	
	public default DBCondition and(String one, String two) {
		return this.and(one, two, "=");
	}
	
	public DBCondition and(String one, String two, String equal);
	
	public DBCondition and(SysVar var);
	
}
