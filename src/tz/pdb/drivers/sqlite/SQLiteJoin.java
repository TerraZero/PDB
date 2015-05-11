package tz.pdb.drivers.sqlite;

import tz.pdb.api.statments.DBJoin;

/**
 * 
 * @author terrazero
 * @created May 7, 2015
 * 
 * @file SQLiteJoin.java
 * @project PDB
 * @identifier tz.pdb.drivers.sqlite
 *
 */
public class SQLiteJoin extends SQLiteCondition implements DBJoin {

	private String type;
	private String table;
	private String alias;
	
	public SQLiteJoin(String one, String two, String equal, String operation) {
		super(one, two, equal, operation);
	}

	/* 
	 * @see tz.pdb.api.statments.DBJoin#table()
	 */
	@Override
	public String table() {
		return this.table;
	}

	/* 
	 * @see tz.pdb.api.statments.DBJoin#alias()
	 */
	@Override
	public String alias() {
		return this.alias;
	}

	/* 
	 * @see tz.pdb.api.statments.DBJoin#type()
	 */
	@Override
	public String type() {
		return this.type;
	}
	
	/* 
	 * @see tz.pdb.api.base.DBExecute#execute()
	 */
	@Override
	public String create() {
		String s = "";
		if (this.type != null) {
			s += this.type.toUpperCase() + " JOIN";
			s += " " + this.table + " AS " + this.alias + " ON";
		}
		s += super.create();
		return s;
	}

	/* 
	 * @see tz.pdb.api.statments.DBJoin#isHead()
	 */
	@Override
	public boolean isHead() {
		return this.type != null;
	}

	/* 
	 * @see tz.pdb.api.statments.DBJoin#head(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public DBJoin head(String type, String table, String alias) {
		this.type = type;
		this.table = table;
		this.alias = alias;
		return this;
	}

}
