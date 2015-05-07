package tz.pdb.drivers.sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tz.pdb.api.DBSelect;
import tz.pdb.api.DBVar;
import tz.pdb.api.statments.DBCondition;
import tz.pdb.api.statments.DBJoin;
import tz.pdb.api.statments.DBOrder;

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
public class SQLiteSelect implements DBSelect {
	
	public static final String DEFAULT_FIELD_TABLE = "FIELDS";
	
	private String table;
	private String alias;
	
	private Map<String, String> tables;
	private Map<String, List<DBVar>> fields;
	private List<SQLiteJoinNode> joins;
	private List<SQLiteCondition> conditions;
	
	public SQLiteSelect() {
		this.tables = new HashMap<String, String>();
		this.fields = new HashMap<String, List<DBVar>>();
		this.joins = new ArrayList<SQLiteJoinNode>();
		this.conditions = new ArrayList<SQLiteCondition>();
	}

	/* 
	 * @see tz.pdb.api.base.DBExecute#execute()
	 */
	@Override
	public String execute() {
		String s = "SELECT ";
		
		StringBuilder string = new StringBuilder();
		this.fields.forEach((alias, list) -> {
			list.forEach((var) -> {
				for (int i = 0; i < var.value().length; i++) {
					string.append(", " + alias + "." + var.value()[i]); 
				}
			}); 
		});
		s += string.substring(2).toString();
		
		s += " FROM " + this.table + " AS " + this.alias;
		
		string.setLength(0);
		this.joins.forEach((join) -> {
			string.append(" " + join.execute());
		});
		s += string.toString();
		
		if (this.conditions.size() > 0) {
			s += " WHERE";
			string.setLength(0);
			this.conditions.forEach((condition) -> {
				string.append(" AND (" + condition.execute() + " )");
			});
			s += string.substring(4).toString();
		}
		
		return s;
	}

	/* 
	 * @see tz.pdb.api.DBSelect#fields(java.lang.String, tz.pdb.api.DBVar)
	 */
	@Override
	public DBSelect fields(String table, DBVar fields) {
		List<DBVar> list = this.fields.get(table);
		if (list == null) {
			list = new ArrayList<DBVar>();
			this.fields.put(table, list);
		}
		list.add(fields);
		return this;
	}

	/* 
	 * @see tz.pdb.api.DBSelect#fields(tz.pdb.api.DBVar)
	 */
	@Override
	public DBSelect fields(DBVar var) {
		return this.fields(SQLiteSelect.DEFAULT_FIELD_TABLE, var);
	}

	/* 
	 * @see tz.pdb.api.DBSelect#join(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public DBJoin join(String type, String table, String alias, String one, String two, String equal) {
		SQLiteJoinNode node = new SQLiteJoinNode(type, table, alias, one, two, equal);
		this.joins.add(node);
		return node.join();
	}

	/* 
	 * @see tz.pdb.api.DBSelect#join(tz.pdb.api.DBVar)
	 */
	@Override
	public DBJoin join(DBVar var) {
		return null;
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
	 * @see tz.pdb.api.DBSelect#where(tz.pdb.api.DBVar)
	 */
	@Override
	public DBCondition where(DBVar var) {
		return null;
	}

	/* 
	 * @see tz.pdb.api.DBSelect#order(java.lang.String, java.lang.String)
	 */
	@Override
	public DBOrder order(String field, String direction) {
		return null;
	}

	/* 
	 * @see tz.pdb.api.DBSelect#order(tz.pdb.api.DBVar)
	 */
	@Override
	public DBOrder order(DBVar var) {
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
	 * @see tz.pdb.api.DBSelect#limit(tz.pdb.api.DBVar)
	 */
	@Override
	public DBSelect limit(DBVar var) {
		return null;
	}

	/* 
	 * @see tz.pdb.api.DBSelect#hasTable(java.lang.String)
	 */
	@Override
	public boolean hasTable(String table) {
		return false;
	}

	/* 
	 * @see tz.pdb.api.DBSelect#tableAlias(java.lang.String)
	 */
	@Override
	public String tableAlias(String table) {
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

}
