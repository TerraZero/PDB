package tz.pdb.api.base;

import tz.pdb.api.DBDriver;

/**
 * 
 * @author terrazero
 * @created May 8, 2015
 * 
 * @file DBStatement.java
 * @project PDB
 * @identifier tz.pdb.api.base
 *
 */
public interface DBStatement extends DBBuilt {

	public String statement();
	
	public DBDriver driver();
	
	public DBStatement driver(DBDriver driver);
	
	public void exe();
	
	public DBStatement placeholder(String placeholder, String value);
	
	public String ident();
	
}
