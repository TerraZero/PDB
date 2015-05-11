package tz.pdb.drivers.sqlite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tz.core.logger.Log;
import tz.pdb.api.DBTable;
import tz.pdb.api.statments.DBField;
import tz.pdb.api.statments.SQLiteStatement;
import tz.pdb.drivers.sqlite.statements.SQLiteField;

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
public class SQLiteTable extends SQLiteStatement implements DBTable {
	
	private String name;
	private List<DBField> fields;
	private List<String> keys;
	
	public SQLiteTable() {
		this.init();
	}

	public SQLiteTable(String table) {
		this.init();
		this.name(table);
	}
	
	protected void init() {
		this.fields = new ArrayList<DBField>();
		this.keys = new ArrayList<String>();
	}

	/* 
	 * @see tz.pdb.api.base.DBStatement#statement()
	 */
	@Override
	public String statement() {
		return this.create();
	}

	/* 
	 * @see tz.pdb.api.base.DBCreate#create()
	 */
	@Override
	public String create() {
		StringBuilder string = new StringBuilder("CREATE TABLE ");
		string.append(this.name).append(" (");
		
		this.fields.forEach((field) -> {
			string.append(field.name()).append(" ").append(field.type().toUpperCase());
			if (field.size() > 0) {
				string.append("(").append(field.size()).append(")");
			}
			for (String additional : field.additionals()) {
				string.append(" ").append(additional);
			}
			string.append(", ");
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
	public DBTable field(String name, String type, String... additionals) {
		this.fields.add(new SQLiteField(name, type, additionals));
		return this;
	}
	
	/* 
	 * @see tz.pdb.api.DBTable#field(java.lang.String, java.lang.String, int, java.lang.String[])
	 */
	@Override
	public DBTable field(String name, String type, int size, String... additionals) {
		this.fields.add(new SQLiteField(name, type, size, additionals));
		return this;
	}

	/* 
	 * @see tz.pdb.api.DBTable#key(java.lang.String)
	 */
	@Override
	public DBTable keys(String... keys) {
		for (String key : keys) {
			this.keys.add(key);
		}
		return this;
	}

	/* 
	 * @see tz.pdb.api.DBTable#fields()
	 */
	@Override
	public List<DBField> fields() {
		return this.fields;
	}

	/* 
	 * @see tz.pdb.api.DBTable#name(java.lang.String)
	 */
	@Override
	public DBTable name(String name) {
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
			Log.fatal(Log.ident("DB", "Driver", "SQLite", "Table"), "Can not execute the table statement.");
			return -1;
		}
	}

	@Override
	public void exe() {
		this.execute();
	}

}
