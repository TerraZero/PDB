package tz.pdb.api.statements;

import tz.pdb.api.base.DBStatement;

public interface DBInfo extends DBStatement {
	
	public static final String TYPE = "info";
	
	public default String type() {
		return DBInfo.TYPE;
	}

	public default String[] tables() {
		return this.tables(false);
	}
	
	public String[] tables(boolean force);
	
	public String autoIncrement();
	
}
