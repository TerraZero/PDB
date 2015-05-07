package tz.pdb.api;

import tz.pdb.api.base.DBExecute;

/**
 * 
 * @author terrazero
 * @created Apr 8, 2015
 * 
 * @file SysVar.java
 * @project SysSQL
 * @identifier TZ.sql
 *
 */
public class DBVar implements DBExecute {

	protected String[] values;
	
	public DBVar(DBExecute execute) {
		this(execute.execute());
	}
	
	public DBVar(String... values) {
		this.values = values;
	}
	
	public String[] value() {
		return this.values;
	}
	
	public String execute() {
		if (this.values == null) {
			return "";
		} else if (this.values.length == 1) {
			return this.values[0];
		} else {
			String s = this.values[0];
			for (int i = 1; i < this.values.length; i++) {
				s += ", " + this.values[i];
			}
			return "(" + s + ")";
		}
	}
	
}
