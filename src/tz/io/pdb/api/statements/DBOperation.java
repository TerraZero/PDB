package tz.io.pdb.api.statements;

import tz.io.pdb.api.base.DBStatement;

public interface DBOperation extends DBStatement {
	
	public static final String TYPE = "operation";
	
	public default String type() {
		return DBOperation.TYPE;
	}

	public int truncate(String table);
	
	public int drop(String table);
	
}
