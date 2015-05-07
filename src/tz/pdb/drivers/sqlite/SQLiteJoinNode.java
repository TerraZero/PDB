package tz.pdb.drivers.sqlite;

import tz.pdb.api.base.DBExecute;

/**
 * 
 * @author terrazero
 * @created May 7, 2015
 * 
 * @file SQLiteJoinNode.java
 * @project PDB
 * @identifier tz.pdb.drivers.sqlite
 *
 */
public class SQLiteJoinNode implements DBExecute{

	private String type;
	private String table;
	private String alias;
	private SQLiteJoin join;
	
	public SQLiteJoinNode(String type, String table, String alias, String one, String two, String equal) {
		this.type = type;
		this.table = table;
		this.alias = alias;
		this.join = new SQLiteJoin(one, two, equal, null);
	}
	
	public SQLiteJoin join() {
		return this.join;
	}
	
	public String type() {
		return this.type;
	}
	
	public String table() {
		return this.table;
	}
	
	public String alias() {
		return this.alias;
	}

	/* 
	 * @see tz.pdb.api.base.DBExecute#execute()
	 */
	@Override
	public String execute() {
		String s = this.type.toUpperCase() + " JOIN";
		s += " " + this.table + " AS " + this.alias + " ON";
		s += this.join.execute();
		return s;
	}
	
}
