package tz.pdb.api;

import java.util.List;

import tz.pdb.api.base.DBUpdating;
import tz.pdb.api.statments.DBField;

/**
 * 
 * @author terrazero
 * @created May 11, 2015
 * 
 * @file DBTable.java
 * @project PDB
 * @identifier tz.pdb.api
 *
 */
public interface DBTable extends DBUpdating {

	public DBTable name(String name);
	
	public String name();
	
	public DBTable field(String name, String type, String... additionals);
	
	public DBTable field(String name, String type, int size, String... additionals);
	
	public DBTable keys(String... keys);
	
	public List<DBField> fields();
	
}
