package tz.pdb.api.statements;

import tz.pdb.api.base.DBQuerieing;

public interface DBQuery extends DBQuerieing {

	public String query();
	
	public DBQuery query(String query);
	
}
