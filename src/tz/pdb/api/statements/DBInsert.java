package tz.pdb.api.statements;

import java.util.List;

import tz.pdb.api.base.DBUpdating;
import tz.pdb.api.fields.DBRow;

public interface DBInsert extends DBUpdating {
	
	public static final String TYPE = "insert";
	
	public default String type() {
		return DBInsert.TYPE;
	}

	public DBInsert table(String table);
	
	public DBInsert cols(String... columns);
	
	public DBInsert row(String... columns);
	
	public String table();
	
	public String[] cols();
	
	public List<DBRow> rows();
	
}
