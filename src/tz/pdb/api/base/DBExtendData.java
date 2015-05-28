package tz.pdb.api.base;

import tz.bases.Extendable;

/**
 * 
 * @author terrazero
 * @created May 19, 2015
 * 
 * @file DBExtendData.java
 * @project PDB
 * @identifier tz.pdb.api.base
 *
 */
public interface DBExtendData extends Extendable<DBExtendData> {
	
	public String extend();
	
	public DBExtendData extend(String extend);
	
	public boolean isLoaded();
	
	public DBExtendData loaded(boolean loaded);
	
}
