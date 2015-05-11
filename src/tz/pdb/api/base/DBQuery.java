package tz.pdb.api.base;

import java.sql.ResultSet;

public interface DBQuery extends DBStatement {

	public ResultSet execute();
	
}
