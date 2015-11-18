package tz.pdb.drivers.sql.statements;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tz.pdb.api.fields.DBCondition;
import tz.pdb.api.functions.DBPlaceholder;
import tz.pdb.api.functions.DBResult;
import tz.pdb.api.statements.DBUpdate;
import tz.pdb.drivers.sql.fields.SQLCondition;
import tz.pdb.drivers.sql.fields.SQLStatement;
import tz.sys.SysUtil;

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
public class SQLUpdate extends SQLStatement implements DBUpdate {
	
	private String table;
	private List<SQLCondition> conditions;
	private Map<String, String> values;
	
	public SQLUpdate() {
		this.init();
	}
	
	public SQLUpdate(String table) {
		this.init(); 
		this.table(table);
	}
	
	protected void init() {
		this.conditions = new ArrayList<SQLCondition>();
		this.values = new HashMap<String, String>();
	}

	/* 
	 * @see tz.pdb.api.base.DBUpdating#execute()
	 */
	@Override
	public int execute() {
		try {
			return this.driver().execute().executeUpdate(this.statement());
		} catch (SQLException e) {
			SysUtil.error("Can not execute the update statement.");
			return -1;
		}
	}

	/* 
	 * @see tz.pdb.api.statments.SQLiteStatement#ident()
	 */
	@Override
	public String ident() {
		return "DB:Driver::SQLite::Update";
	}
	
	/* 
	 * @see tz.pdb.api.base.DBStatement#exe()
	 */
	@Override
	public DBResult exe() {
		String statement = this.statement();
		try {
			return new DBResult(statement, this.type(), this.driver().execute().executeUpdate(statement));
		} catch (SQLException e) {
			SysUtil.error("Can not execute the update statement.");
			return new DBResult(statement, this.type(), e);
		}
	}

	/* 
	 * @see tz.pdb.api.base.DBCreate#create()
	 */
	@Override
	public String built() {
		StringBuilder sb = new StringBuilder("UPDATE ");
		sb.append(this.table).append(" SET ");
		this.values.forEach((col, value) -> {
			sb.append(col).append(" = ").append(DBPlaceholder.renderValue(value));
		});
		
		if (this.conditions.size() > 0) {
			sb.append(" WHERE ");
			StringBuilder con = new StringBuilder();
			this.conditions.forEach((condition) -> {
				con.append(" AND (" + condition.built() + " )");
			});
			sb.append(con.substring(5).toString());
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
	 * @see tz.pdb.api.DBUpdate#update(java.lang.String, java.lang.String)
	 */
	@Override
	public DBUpdate update(String col, String value) {
		this.values.put(col, value);
		return this;
	}

	/* 
	 * @see tz.pdb.api.DBUpdate#set(java.lang.String, java.lang.String)
	 */
	@Override
	public DBUpdate set(String col, String value) {
		return this.update(col, DBPlaceholder.maskValue(value));
	}

	/* 
	 * @see tz.pdb.api.DBUpdate#set(java.lang.String, int)
	 */
	@Override
	public DBUpdate set(String col, int value) {
		return this.update(col, DBPlaceholder.maskValue(value));
	}

	/* 
	 * @see tz.pdb.api.DBUpdate#where(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public DBCondition where(String one, String two, String equal) {
		SQLCondition condition = new SQLCondition(one, two, equal, null);
		this.conditions.add(condition);
		return condition;
	}

}
