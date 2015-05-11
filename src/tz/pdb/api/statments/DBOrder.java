package tz.pdb.api.statments;

import tz.pdb.api.DBVar;
import tz.pdb.api.base.DBChain;
import tz.pdb.api.base.DBCreate;

/**
 * 
 * @author terrazero
 * @created Apr 8, 2015
 * 
 * @file SysOrder.java
 * @project SysSQL
 * @identifier TZ.sql.api
 *
 */
public interface DBOrder extends DBCreate, DBChain<DBOrder> {

	public DBOrder asc(String field);
	
	public DBOrder desc(String field);
	
	public DBOrder order(String field, String direction);
	
	public DBOrder order(DBVar var);
	
}
