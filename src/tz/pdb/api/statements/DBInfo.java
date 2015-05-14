package tz.pdb.api.statements;

import tz.pdb.api.base.DBStatement;

public interface DBInfo extends DBStatement {

	public default String[] tables() {
		return this.tables(false);
	}
	
	public String[] tables(boolean force);
	
}
