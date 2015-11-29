package tz.io.pdb.api.base;

import java.util.List;

import tz.io.pdb.api.DBDriver;
import tz.io.pdb.api.functions.DBResult;
import tz.sys.api.Extendable;

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
public interface DBStatement extends DBBuilt, Extendable<DBStatement> {
	
	public String type();

	public String statement();
	
	public DBDriver driver();
	
	public DBStatement driver(DBDriver driver);
	
	public DBResult exe();
	
	public DBStatement placeholder(String placeholder, String value);
	
	public String ident();
	
	public default DBStatement extend(String extend) {
		return this.extend(extend, null);
	}
	
	public DBStatement extend(String extend, DBExtendData data);
	
	public List<DBExtendData> extend();
	
}
