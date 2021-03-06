package tz.io.pdb.drivers.sql.statements;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import tz.io.pdb.api.fields.DBCondition;
import tz.io.pdb.api.fields.DBField;
import tz.io.pdb.api.fields.DBJoin;
import tz.io.pdb.api.fields.DBOrder;
import tz.io.pdb.api.functions.DBResult;
import tz.io.pdb.api.statements.DBSelect;
import tz.io.pdb.drivers.sql.fields.SQLCondition;
import tz.io.pdb.drivers.sql.fields.SQLField;
import tz.io.pdb.drivers.sql.fields.SQLJoin;
import tz.io.pdb.drivers.sql.fields.SQLStatement;
import tz.sys.Sys;

/**
 * 
 * @author terrazero
 * @created May 7, 2015
 * 
 * @file SQLiteSelect.java
 * @project PDB
 * @identifier tz.pdb.drivers.sqlite
 *
 */
public class SQLSelect extends SQLStatement implements DBSelect {
	
	private String table;
	private String alias;
	private List<SQLJoin> joins;
	private Map<String, DBField> fields;
	private boolean selectAll;
	private String selectAllFunction;
	private List<SQLCondition> conditions;
	
	public SQLSelect() {
		this.init();
	}

	public SQLSelect(String from, String alias) {
		this.init();
		this.from(from, alias);
	}
	
	protected void init() {
		this.joins = new ArrayList<SQLJoin>();
		this.fields = new HashMap<String, DBField>();
		this.conditions = new ArrayList<SQLCondition>();
	}

	/* 
	 * @see tz.pdb.api.base.DBExecute#execute()
	 */
	@Override
	public String built() {
		String s = "SELECT ";
		
		StringBuilder string = new StringBuilder();
		if (this.selectAll) {
			if (this.selectAllFunction == null) {
				s += "*";
			} else {
				s += this.selectAllFunction.toUpperCase() + "(*)";
			}
		} else {
			this.fields.forEach((alias, field) -> {
				string.append(", ").append(field.built());
			});
			s += string.substring(2);
		}
		
		s += " FROM " + this.table + " AS " + this.alias;
		
		string.setLength(0);
		this.joins.forEach((join) -> {
			string.append(" " + join.built());
		});
		s += string.toString();
		
		if (this.conditions.size() > 0) {
			s += " WHERE";
			string.setLength(0);
			this.conditions.forEach((condition) -> {
				string.append(" AND (" + condition.built() + " )");
			});
			s += string.substring(4).toString();
		}
		
		for (Entry<String, String> entry : this.tables().entrySet()) {
			s = s.replaceAll("\\?\\[" + entry.getKey() + "\\]", entry.getValue());
		}
		
		return s;
	}
	
	/* 
	 * @see tz.pdb.api.statments.SQLiteStatement#ident()
	 */
	@Override
	public String ident() {
		return "DB::Driver::SQLite::Select";
	}

	/* 
	 * @see tz.pdb.api.DBSelect#join(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public DBJoin join(String type, String table, String alias, String one, String two, String equal) {
		SQLJoin join = new SQLJoin(one, two, equal, null);
		this.joins.add(join);
		return join.head(type, table, alias);
	}
	
	/* 
	 * @see tz.pdb.api.DBSelect#where(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public DBCondition where(String one, String two, String equal) {
		SQLCondition condition = new SQLCondition(one, two, equal, null);
		this.conditions.add(condition);
		return condition;
	}

	/* 
	 * @see tz.pdb.api.DBSelect#order(java.lang.String, java.lang.String)
	 */
	@Override
	public DBOrder order(String field, String direction) {
		return null;
	}

	/* 
	 * @see tz.pdb.api.DBSelect#limit(int, int)
	 */
	@Override
	public DBSelect limit(int offset, int length) {
		return null;
	}

	/* 
	 * @see tz.pdb.api.DBSelect#hasTable(java.lang.String)
	 */
	@Override
	public boolean hasTable(String table) {
		if (table.equals(this.table)) return true;
		Iterator<SQLJoin> i = this.joins.iterator();
		while (i.hasNext()) {
			if (i.next().table().equals(table)) return true;
		}
		return false;
	}

	/* 
	 * @see tz.pdb.api.DBSelect#tableAlias(java.lang.String)
	 */
	@Override
	public String table(String table) {
		if (table.equals(this.table)) return this.alias;
		Iterator<SQLJoin> i = this.joins.iterator();
		while (i.hasNext()) {
			SQLJoin join = i.next();
			if (join.table().equals(table)) return join.alias();
		}
		return null;
	}
	
	

	/* 
	 * @see tz.pdb.api.DBSelect#from(java.lang.String, java.lang.String)
	 */
	@Override
	public DBSelect from(String table, String alias) {
		this.table = table;
		this.alias = alias;
		return this;
	}

	@Override
	public DBResult exe() {
		String statement = this.statement();
		try {
			return new DBResult(statement, this.type(), this.driver().execute().executeQuery(statement));
		} catch (SQLException e) {
			Sys.error("Can not execute the select statement.");
			Sys.exception(e);
			return new DBResult(statement, this.type(), e);
		}
	}

	@Override
	public ResultSet execute() {
		try {
			return this.driver().execute().executeQuery(this.statement());
		} catch (SQLException e) {
			Sys.error("Can not execute the select statement.");
			return null;
		}
	}

	@Override
	public DBSelect field(String table, String field, String alias, String function) {
		if (this.selectAll) {
			Sys.warn("Add a field has no use because all are selected. Field [0] will be add but ignored by building.", table + "." + field);
		}
		SQLField addfield = new SQLField(table, field, alias, function);
		DBField f = null;
		if ((f = this.fields.put(alias, addfield)) != null) {
			Sys.warn("Duplicate field alias [0] for field [1] and field [2]!", addfield.alias(), f.table() + "." + f.field(), addfield.table() + "." + addfield.field());
		}
		return this;
	}

	@Override
	public DBSelect fields(DBField... fields) {
		if (this.selectAll) {
			Sys.error("Add a field has no use because all are selected. [0] fields will be add but ignored by building.", fields.length + "");
		}
		DBField f = null;
		for (DBField field : fields) {
			if ((f = this.fields.put(field.alias(), field)) != null) {
				Sys.warn("Duplicate field alias [0] for field [1] and field [2]!", field.alias(), f.table() + "." + f.field(), field.table() + "." + field.field());
			}
		}
		return this;
	}

	@Override
	public Map<String, DBField> fields() {
		return this.fields;
	}

	@Override
	public boolean hasField(String alias) {
		return this.fields.containsKey(alias);
	}

	@Override
	public DBSelect selectAll() {
		this.selectAll = true;
		return this;
	}

	@Override
	public boolean isSelectAll() {
		return this.selectAll;
	}

	@Override
	public DBSelect selectAll(String function) {
		this.selectAll = true;
		this.selectAllFunction = function;
		return this;
	}

	@Override
	public String selectAllFunction() {
		return this.selectAllFunction;
	}

	@Override
	public Map<String, String> tables() {
		Map<String, String> tables = new HashMap<String, String>();
		
		tables.put(this.table, this.alias);
		Iterator<SQLJoin> i = this.joins.iterator();
		while (i.hasNext()) {
			SQLJoin join = i.next();
			tables.put(join.table(), join.alias());
		}
		return tables;
	}

}
