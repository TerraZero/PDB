package tz.io.pdb.api.fields;

import tz.io.pdb.api.base.DBBuilt;

/**
 * 
 * @author terrazero
 * @created Apr 8, 2015
 * 
 * @file SysJoin.java
 * @project SysSQL
 * @identifier TZ.sql.api
 *
 */
public interface DBJoin extends DBCondition, DBBuilt {
	
	public static final String KEY = "#key#";
	
	public String table();
	
	public String alias();
	
	public String type();
	
	public boolean isHead();
	
	public DBJoin head(String type, String table, String alias);
	
}
