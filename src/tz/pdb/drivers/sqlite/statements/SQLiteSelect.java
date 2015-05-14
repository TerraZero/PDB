package tz.pdb.drivers.sqlite.statements;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tz.core.logger.Log;
import tz.pdb.api.fields.DBCondition;
import tz.pdb.api.fields.DBField;
import tz.pdb.api.fields.DBJoin;
import tz.pdb.api.fields.DBOrder;
import tz.pdb.api.statements.DBSelect;
import tz.pdb.drivers.sqlite.fields.SQLiteCondition;
import tz.pdb.drivers.sqlite.fields.SQLiteField;
import tz.pdb.drivers.sqlite.fields.SQLiteJoin;
import tz.pdb.drivers.sqlite.fields.SQLiteStatement;

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
public class SQLiteSelect extends SQLiteStatement implements DBSelect {
	
	public static final String DEFAULT_FIELD_TABLE = "FIELDS";
	
	private String table;
	private String alias;
	private List<SQLiteJoin> joins;
	private Map<String, DBField> fields;
	private boolean selectAll;
	private String selectAllFunction;
	private List<SQLiteCondition> conditions;
	
	public SQLiteSelect() {
		this.init();
	}

	public SQLiteSelect(String from, String alias) {
		this.init();
		this.from(from, alias);
	}
	
	protected void init() {
		this.joins = new ArrayList<SQLiteJoin>();
		this.fields = new HashMap<String, DBField>();
		this.conditions = new ArrayList<SQLiteCondition>();
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
				s += this.selectAllFunction.toUpperCase() + "(*)"; 
			} else {
				s += "*";
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
		
		return s;
	}
	
	/* 
	 * @see tz.pdb.api.statments.SQLiteStatement#ident()
	 */
	@Override
	public String ident() {
		return Log.ident("DB", "Driver", "SQLite", "Select");
	}

	/* 
	 * @see tz.pdb.api.DBSelect#join(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public DBJoin join(String type, String table, String alias, String one, String two, String equal) {
		SQLiteJoin join = new SQLiteJoin(one, two, equal, null);
		this.joins.add(join);
		return join.head(type, table, alias);
	}
	
	/* 
	 * @see tz.pdb.api.DBSelect#where(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public DBCondition where(String one, String two, String equal) {
		SQLiteCondition condition = new SQLiteCondition(one, two, equal, null);
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
		Iterator<SQLiteJoin> i = this.joins.iterator();
		while (i.hasNext()) {
			if (i.next().table().equals(table)) return true;
		}
		return false;
	}

	/* 
	 * @see tz.pdb.api.DBSelect#tableAlias(java.lang.String)
	 */
	@Override
	public String tableAlias(String table) {
		if (table.equals(this.table)) return this.alias;
		Iterator<SQLiteJoin> i = this.joins.iterator();
		while (i.hasNext()) {
			SQLiteJoin join = i.next();
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
	public void exe() {
		this.execute();
	}

	@Override
	public ResultSet execute() {
		try {
			return this.driver().execute().executeQuery(this.statement());
		} catch (SQLException e) {
			System.out.println(e);
			Log.fatal(this.ident(), "Can not execute the select statement.");
			return null;
		}
	}

	@Override
	public DBSelect field(String table, String field, String alias, String function) {
		if (this.selectAll) {
			Log.warning(this.ident(), "Add a field has no use because all are selected. Field [0] will be add but ignored by building.", table + "." + field);
		}
		SQLiteField addfield = new SQLiteField(table, field, alias, function);
		DBField f = null;
		if ((f = this.fields.put(alias, addfield)) != null) {
			Log.warning(this.ident(), "Duplicate field alias [0] for field [1] and field [2]", addfield.alias(), f.table() + "." + f.field(), addfield.table() + "." + addfield.field());
		}
		return this;
	}

	@Override
	public DBSelect fields(DBField... fields) {
		if (this.selectAll) {
			Log.warning(this.ident(), "Add a field has no use because all are selected. [0] fields will be add but ignored by building.", fields.length + "");
		}
		DBField f = null;
		for (DBField field : fields) {
			if ((f = this.fields.put(field.alias(), field)) != null) {
				Log.warning(this.ident(), "Duplicate field alias [0] for field [1] and field [2]", field.alias(), f.table() + "." + f.field(), field.table() + "." + field.field());
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

}
