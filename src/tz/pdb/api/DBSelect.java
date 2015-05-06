package tz.pdb.api;

import tz.pdb.SysVar;
import tz.pdb.api.base.DBExecute;
import tz.pdb.api.statments.DBCondition;
import tz.pdb.api.statments.DBJoin;
import tz.pdb.api.statments.DBOrder;

/**
 * 
 * @author terrazero
 * @created Apr 8, 2015
 * 
 * @file SysSelect.java
 * @project SysSQL
 * @identifier TZ.sql.api
 *
 */
public interface DBSelect extends DBExecute {

	public default DBSelect fields(String table, String... fields) {
		return this.fields(table, new SysVar(fields));
	}
	
	public DBSelect fields(String table, SysVar fields);
	
	public DBSelect fields(SysVar var);
	
	public default DBJoin join(String type, String table, String one, String two) {
		return this.join(type, table, one, two, "=");
	}
	
	public DBJoin join(String type, String table, String one, String two, String equal);
	
	public DBJoin join(SysVar var);
	
	public default DBCondition where(String one, String two) {
		return this.where(one, two, "=");
	}
	
	public DBCondition where(String one, String two, String equal);
	
	public DBCondition where(SysVar var);
	
	public default DBOrder order(String field) {
		return this.order(field, "ASC");
	}
	
	public DBOrder order(String field, String direction);
	
	public DBOrder order(SysVar var);
	
	public default DBSelect limit(int length) {
		return this.limit(0, length);
	}
	
	public DBSelect limit(int offset, int length);
	
	public DBSelect limit(SysVar var);
	
}
