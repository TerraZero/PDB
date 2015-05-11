package tz.pdb.api;

import tz.pdb.api.base.DBStatement;
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
public interface DBSelect extends DBStatement {

	public default DBSelect fields(String table, String... fields) {
		return this.fields(table, new DBVar(fields));
	}
	
	public DBSelect fields(String table, DBVar fields);
	
	public DBSelect fields(DBVar var);
	
	public DBSelect from(String table, String alias);
	
	public default DBJoin join(String type, String table, String alias, String one, String two) {
		return this.join(type, table, alias, one, two, DBCondition.EQUAL);
	}
	
	public DBJoin join(String type, String table, String alias, String one, String two, String equal);
	
	public DBJoin join(DBVar var);
	
	public default DBCondition where(String one, String two) {
		return this.where(one, two, DBCondition.EQUAL);
	}
	
	public DBCondition where(String one, String two, String equal);
	
	public DBCondition where(DBVar var);
	
	public default DBOrder order(String field) {
		return this.order(field, "ASC");
	}
	
	public DBOrder order(String field, String direction);
	
	public DBOrder order(DBVar var);
	
	public default DBSelect limit(int length) {
		return this.limit(0, length);
	}
	
	public DBSelect limit(int offset, int length);
	
	public DBSelect limit(DBVar var);
	
	
	
	public boolean hasTable(String table);
	
	public String tableAlias(String table);
	
	public DBSelect placeholder(String placeholder, String value);
	
}
