package tz.pdb.api.statements;

import tz.pdb.api.base.DBStatement;

public interface DBOperation extends DBStatement {

	public int truncate(String table);
	
	public int drop(String table);
	
}
