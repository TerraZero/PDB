package tz.pdb.drivers.sqlite.statements;

import tz.pdb.api.DBVar;
import tz.pdb.api.statments.DBCondition;

/**
 * 
 * @author terrazero
 * @created May 7, 2015
 * 
 * @file SQLiteCondition.java
 * @project PDB
 * @identifier tz.pdb.drivers.sqlite
 *
 */
public class SQLiteCondition implements DBCondition {
	
	private SQLiteCondition next;
	private String one;
	private String two;
	private String equal;
	private String operation;

	public SQLiteCondition(String one, String two, String equal, String operation) {
		this.one = one;
		this.two = two;
		this.equal = equal;
		this.operation = operation;
	}
	
	/* 
	 * @see tz.pdb.api.base.DBExecute#execute()
	 */
	@Override
	public String create() {
		String s = "";
		if (this.operation != null) {
			s += " " + this.operation;
		}
		s += " " + this.one + " " + this.equal + " " + this.two;
		if (this.next != null) {
			s += this.next.create();
		}
		return s;
	}

	/* 
	 * @see tz.pdb.api.base.DBChain#next()
	 */
	@Override
	public DBCondition next() {
		return this.next;
	}

	/* 
	 * @see tz.pdb.api.base.DBChain#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return this.next != null;
	}

	/* 
	 * @see tz.pdb.api.statments.DBCondition#and(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public DBCondition and(String one, String two, String equal) {
		this.next = new SQLiteCondition(one, two, equal, DBCondition.OP_AND);
		return this.next();
	}
	
	/* 
	 * @see tz.pdb.api.statments.DBCondition#or(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public DBCondition or(String one, String two, String equal) {
		this.next = new SQLiteCondition(one, two, equal, DBCondition.OP_OR);
		return this.next();
	}

	/* 
	 * @see tz.pdb.api.statments.DBCondition#and(tz.pdb.api.DBVar)
	 */
	@Override
	public DBCondition and(DBVar var) {
		return null;
	}

}
