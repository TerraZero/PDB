package tz.io.pdb.api.base;

import java.sql.ResultSet;

public interface DBQuerieing extends DBStatement {

	public ResultSet execute();
	
}
