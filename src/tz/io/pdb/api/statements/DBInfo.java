package tz.io.pdb.api.statements;

import tz.io.pdb.api.base.DBStatement;

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
	
	public DBInfo use(String database);
	
	public String use();
	
	public default String[] databases() {
		return this.databases(false);
	}
	
	public String[] databases(boolean force);
	
}
