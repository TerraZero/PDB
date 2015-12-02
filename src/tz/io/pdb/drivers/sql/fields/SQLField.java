package tz.io.pdb.drivers.sql.fields;

import tz.io.pdb.api.fields.DBField;

public class SQLField implements DBField {
	
	private String table;
	private String field;
	private String alias;
	private String function;
	
	public SQLField() {
		
	}
	
	public SQLField(String table, String field, String alias, String function) {
		this.table = table;
		this.field = field;
		this.alias = alias;
		this.function = function;
	}

	@Override
	public String built() {
		String s = "";
		if (this.function != null) {
			s += this.function.toUpperCase() + "(";
		}
		s += this.table + "." + this.field;
		if (this.function != null) {
			s += ")";
		}
		return s + " AS " + this.alias;
	}

	@Override
	public String table() {
		return this.table;
	}

	@Override
	public String field() {
		return this.field;
	}

	@Override
	public String alias() {
		return this.alias;
	}

	@Override
	public String function() {
		return this.function;
	}

	@Override
	public DBField table(String table) {
		this.table = table;
		return this;
	}

	@Override
	public DBField field(String field) {
		this.field = field;
		return this;
	}

	@Override
	public DBField alias(String alias) {
		this.alias = alias;
		return this;
	}

	@Override
	public DBField function(String function) {
		this.function = function;
		return this;
	}

}
