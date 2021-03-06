package tz.io.pdb.api.functions;

import tz.io.pdb.api.base.DBExtendData;
import tz.io.pdb.api.base.DBStatement;

/**
 * 
 * @author terrazero
 * @created May 21, 2015
 * 
 * @file DBExtender.java
 * @project PDB
 * @identifier tz.pdb.api
 *
 */
public interface DBExtender {

	public String name();
	
	public String[] type();
	
	public void extend(String type, DBStatement statement, DBExtendData data);
	
	public default String alias(String alias) {
		return this.name().toLowerCase() + "_" + alias;
	}
	
}
