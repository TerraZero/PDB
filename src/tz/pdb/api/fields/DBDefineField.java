package tz.pdb.api.fields;

import tz.pdb.api.base.DBBuilt;

/**
 * 
 * @author terrazero
 * @created May 11, 2015
 * 
 * @file DBField.java
 * @project PDB
 * @identifier tz.pdb.api.statments
 *
 */
public interface DBDefineField extends DBBuilt {

	public String name();
	
	public String type();
	
	public int size();
	
	public String[] additionals();
	
}
