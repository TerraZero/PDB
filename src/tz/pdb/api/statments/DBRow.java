package tz.pdb.api.statments;

import tz.pdb.api.base.DBCreate;

public interface DBRow extends DBCreate {
	
	public DBRow setHead(String[] cols);

	public String[] values();
	
	public String value(String col);
	
}
