package tz.pdb.api.base;

/**
 * 
 * @author terrazero
 * @created Apr 8, 2015
 * 
 * @file SysChain.java
 * @project SysSQL
 * @identifier TZ.sql.api
 *
 */
public interface DBChain<chain> {

	public chain next();
	
	public boolean hasNext();
	
}
