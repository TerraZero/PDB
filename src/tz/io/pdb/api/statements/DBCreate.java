package tz.io.pdb.api.statements;

import java.util.List;

import tz.io.pdb.api.base.DBUpdating;
import tz.io.pdb.api.fields.DBDefineField;

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
public interface DBCreate extends DBUpdating {
	
	public static final String TYPE = "create";
	
	public default String type() {
		return DBCreate.TYPE;
	}

	public DBCreate name(String name);
	
	public String name();
	
	public DBCreate field(String name, String type, String... additionals);
	
	public DBCreate field(String name, String type, int size, String... additionals);
	
	public DBCreate field(DBDefineField field);
	
	public DBCreate keys(String... keys);
	
	public List<DBDefineField> fields();
	
}
