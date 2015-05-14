package tz.pdb.api.fields;

import tz.pdb.api.base.DBBuilt;

public interface DBRow extends DBBuilt {
	
	public DBRow setHead(String[] cols);

	public String[] values();
	
	public String value(String col);
	
}
