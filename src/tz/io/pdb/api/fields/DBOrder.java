package tz.io.pdb.api.fields;

import tz.io.pdb.api.base.DBBuilt;
import tz.io.pdb.api.base.DBChain;

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
public interface DBOrder extends DBBuilt, DBChain<DBOrder> {

	public DBOrder asc(String field);
	
	public DBOrder desc(String field);
	
	public DBOrder order(String field, String direction);
	
}
