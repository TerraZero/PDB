package tz.pdb.api.statements;

import tz.pdb.api.base.DBQuerieing;

public interface DBQuery extends DBQuerieing {
	
	public static final String TYPE = "query";
	
	public default String type() {
		return DBQuery.TYPE;
	}

	public String query();
	
	public DBQuery query(String query);
	
}
