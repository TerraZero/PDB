package tz.pdb.drivers.sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tz.core.logger.Log;
import tz.pdb.SQLPlaceholder;
import tz.pdb.api.DBUpdate;
import tz.pdb.api.statments.DBCondition;
import tz.pdb.api.statments.SQLiteStatement;
import tz.pdb.drivers.sqlite.statements.SQLiteCondition;

/**
 * 
 * @author terrazero
 * @created May 12, 2015
 * 
 * @file SQLiteUpdate.java
 * @project PDB
 * @identifier tz.pdb.drivers.sqlite
 *
 */
public class SQLiteUpdate extends SQLiteStatement implements DBUpdate {
	
	private String table;
	private List<SQLiteCondition> conditions;
	private Map<String, String> values;
	
	public SQLiteUpdate() {
		this.init();
	}
	
	public SQLiteUpdate(String table) {
		this.init(); 
		this.table(table);
	}
	
	protected void init() {
		this.conditions = new ArrayList<SQLiteCondition>();
		this.values = new HashMap<String, String>();
	}

	/* 
	 * @see tz.pdb.api.base.DBUpdating#execute()
	 */
	@Override
	public int execute() {
		return 0;
	}

	/* 
	 * @see tz.pdb.api.statments.SQLiteStatement#ident()
	 */
	@Override
	public String ident() {
		return Log.ident("DB", "Driver", "SQLite", "Update");
	}
	
	/* 
	 * @see tz.pdb.api.base.DBStatement#exe()
	 */
	@Override
	public void exe() {
		this.execute();
	}

	/* 
	 * @see tz.pdb.api.base.DBCreate#create()
	 */
	@Override
	public String create() {
		StringBuilder sb = new StringBuilder("UPDATE ");
		sb.append(this.table).append(" SET ");
		this.values.forEach((col, value) -> {
			sb.append(col).append(" = ").append(SQLPlaceholder.renderValue(value, this.ident()));
		});
		
		if (this.conditions.size() > 0) {
			sb.append(" WHERE ");
			StringBuilder con = new StringBuilder();
			this.conditions.forEach((condition) -> {
				con.append(" AND (" + condition.create() + " )");
			});
			sb.append(con.substring(4).toString());
		}
		return sb.toString();
	}

	/* 
	 * @see tz.pdb.api.DBUpdate#table()
	 */
	@Override
	public String table() {
		return this.table;
	}

	/* 
	 * @see tz.pdb.api.DBUpdate#table(java.lang.String)
	 */
	@Override
	public DBUpdate table(String table) {
		this.table = table;
		return this;
	}

	/* 
	 * @see tz.pdb.api.DBUpdate#set(java.lang.String, java.lang.String)
	 */
	@Override
	public DBUpdate set(String col, String value) {
		this.values.put(col, value);
		return this;
	}

	/* 
	 * @see tz.pdb.api.DBUpdate#where(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public DBCondition where(String one, String two, String equal) {
		SQLiteCondition condition = new SQLiteCondition(one, two, equal, null);
		this.conditions.add(condition);
		return condition;
	}

}
