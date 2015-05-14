package tz.pdb.drivers.sqlite.fields;

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
public class SQLiteDefineField implements DBDefineField {
	
	private String name;
	private String type;
	private int size;
	private String[] additionals;
	
	public SQLiteDefineField(String name, String type, String[] additionals) {
		this.name = name;
		this.type = type;
		this.additionals = additionals;
	}
	
	public SQLiteDefineField(String name, String type, int size, String[] additionals) {
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
		// TODO Auto-generated method stub
		return null;
	}

}
