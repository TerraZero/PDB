package tz.io.pdb.drivers.sql.statements;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tz.io.pdb.api.fields.DBDefineField;
import tz.io.pdb.api.functions.DBResult;
import tz.io.pdb.api.statements.DBCreate;
import tz.io.pdb.drivers.sql.fields.SQLDefineField;
import tz.io.pdb.drivers.sql.fields.SQLStatement;
import tz.sys.Sys;

/**
 * 
 * @author terrazero
 * @created May 11, 2015
 * 
 * @file SQLiteTable.java
 * @project PDB
 * @identifier tz.pdb.drivers.sqlite
 *
 */
public class SQLCreate extends SQLStatement implements DBCreate {
	
	private String name;
	private List<DBDefineField> fields;
	private List<String> keys;
	
	public SQLCreate() {
		this.init();
	}

	public SQLCreate(String table) {
		this.init();
		this.name(table);
	}
	
	protected void init() {
		this.fields = new ArrayList<DBDefineField>();
		this.keys = new ArrayList<String>();
	}

	/* 
	 * @see tz.pdb.api.statments.SQLiteStatement#ident()
	 */
	@Override
	public String ident() {
		return "DB::Driver::SQLite::Table";
	}

	/* 
	 * @see tz.pdb.api.base.DBCreate#create()
	 */
	@Override
	public String built() {
		StringBuilder string = new StringBuilder("CREATE TABLE ");
		string.append(this.name).append(" (");
		
		this.fields.forEach((field) -> {
			string.append(field.built()).append(" ");
		});
		
		if (this.keys.size() > 0) {
			string.append("CONSTRAINT ").append(this.name.toLowerCase()).append("_id PRIMARY KEY (");
			for (String key : this.keys) {
				string.append(key).append(", ");
			}
			string.setLength(string.length() - 2);
			string.append(")");
		} else {
			// remove last comma from field definition
			string.setLength(string.length() - 2);
		}
		
		return string.append(")").toString();
	}

	/* 
	 * @see tz.pdb.api.DBTable#field(java.lang.String, java.lang.String, java.lang.String[])
	 */
	@Override
	public DBCreate field(String name, String type, String... additionals) {
		this.fields.add(new SQLDefineField(name, type, additionals));
		return this;
	}
	
	/* 
	 * @see tz.pdb.api.DBTable#field(java.lang.String, java.lang.String, int, java.lang.String[])
	 */
	@Override
	public DBCreate field(String name, String type, int size, String... additionals) {
		this.fields.add(new SQLDefineField(name, type, size, additionals));
		return this;
	}

	/* 
	 * @see tz.pdb.api.DBTable#key(java.lang.String)
	 */
	@Override
	public DBCreate keys(String... keys) {
		for (String key : keys) {
			this.keys.add(key);
		}
		return this;
	}

	/* 
	 * @see tz.pdb.api.DBTable#fields()
	 */
	@Override
	public List<DBDefineField> fields() {
		return this.fields;
	}

	/* 
	 * @see tz.pdb.api.DBTable#name(java.lang.String)
	 */
	@Override
	public DBCreate name(String name) {
		this.name = name;
		return this;
	}

	/* 
	 * @see tz.pdb.api.DBTable#name()
	 */
	@Override
	public String name() {
		return this.name;
	}

	@Override
	public int execute() {
		try {
			return this.driver().execute().executeUpdate(this.statement());
		} catch (SQLException e) {
			Sys.error("Can not execute the table statement.");
			return -1;
		}
	}

	@Override
	public DBResult exe() {
		String statement = this.statement();
		try {
			return new DBResult(statement, this.type(), this.driver().execute().executeUpdate(statement));
		} catch (SQLException e) {
			Sys.error("Can not execute the table statement.");
			return new DBResult(statement, this.type(), e);
		}
	}

	@Override
	public DBCreate field(DBDefineField field) {
		this.fields.add(field);
		return this;
	}

}
