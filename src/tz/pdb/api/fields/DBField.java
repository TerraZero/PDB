package tz.pdb.api.fields;

import tz.pdb.api.base.DBBuilt;

public interface DBField extends DBBuilt {

	public String table();
	
	public String field();
	
	public String alias();
	
	public String function();
	
	public DBField table(String table);
	
	public DBField field(String field);
	
	public DBField alias(String alias);
	
	public DBField function(String function);
	
}
