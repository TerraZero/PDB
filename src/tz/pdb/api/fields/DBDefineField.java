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
	
	public DBDefineField name(String name);
	
	public String type();
	
	public DBDefineField type(String type);
	
	public int size();
	
	public DBDefineField size(int size);
	
	public String[] additionals();
	
	public DBDefineField additionals(String[] additionals);
	
}
