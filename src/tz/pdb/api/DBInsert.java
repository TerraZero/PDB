package tz.pdb.api;

import java.util.List;

import tz.pdb.api.base.DBUpdate;
import tz.pdb.api.statments.DBRow;

public interface DBInsert extends DBUpdate {

	public DBInsert table(String table);
	
	public DBInsert cols(String... columns);
	
	public DBInsert row(String... columns);
	
	public String table();
	
	public String[] cols();
	
	public List<DBRow> rows();
	
}
