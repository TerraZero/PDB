package tz.pdb.drivers.def.fields;

import tz.pdb.api.fields.DBDefineField;

/**
 * 
 * @author terrazero
 * @created May 11, 2015
 * 
 * @file SQLiteField.java
 * @project PDB
 * @identifier tz.pdb.drivers.sqlite.statements
 *
 */
public class SQLDefDefineField implements DBDefineField {
	
	private String name;
	private String type;
	private int size;
	private String[] additionals;
	
	public SQLDefDefineField() {
		
	}
	
	public SQLDefDefineField(String name, String type, String[] additionals) {
		this.name = name;
		this.type = type;
		this.additionals = additionals;
	}
	
	public SQLDefDefineField(String name, String type, int size, String[] additionals) {
		this.name = name;
		this.type = type;
		this.size = size;
		this.additionals = additionals;
	}

	/* 
	 * @see tz.pdb.api.statments.DBField#name()
	 */
	@Override
	public String name() {
		return this.name;
	}

	/* 
	 * @see tz.pdb.api.statments.DBField#type()
	 */
	@Override
	public String type() {
		return this.type;
	}

	/* 
	 * @see tz.pdb.api.statments.DBField#additional()
	 */
	@Override
	public String[] additionals() {
		return this.additionals;
	}

	/* 
	 * @see tz.pdb.api.statments.DBField#size()
	 */
	@Override
	public int size() {
		return this.size;
	}

	@Override
	public String built() {
		String s = this.name + " " + this.type.toUpperCase();
		if (this.size > 0) {
			s += "(" + this.size + ")";
		}
		for (String additional : this.additionals) {
			s += " " + additional.toUpperCase();
		}
		return s + ",";
	}

	@Override
	public DBDefineField name(String name) {
		this.name = name;
		return this;
	}

	@Override
	public DBDefineField type(String type) {
		this.type = type;
		return this;
	}

	@Override
	public DBDefineField size(int size) {
		this.size = size;
		return this;
	}

	@Override
	public DBDefineField additionals(String[] additionals) {
		this.additionals = additionals;
		return this;
	}

}
