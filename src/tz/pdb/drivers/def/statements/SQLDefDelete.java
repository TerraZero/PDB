package tz.pdb.drivers.def.statements;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tz.pdb.api.fields.DBCondition;
import tz.pdb.api.functions.DBResult;
import tz.pdb.api.statements.DBDelete;
import tz.pdb.drivers.def.fields.SQLDefCondition;
import tz.pdb.drivers.def.fields.SQLDefStatement;
import tz.sys.SysUtil;

/**
 * 
 * @author terrazero
 * @created May 13, 2015
 * 
 * @file SQLiteDelete.java
 * @project PDB
 * @identifier tz.pdb.drivers.sqlite
 *
 */
public class SQLDefDelete extends SQLDefStatement implements DBDelete {
	
	private String table;
	private List<SQLDefCondition> conditions;
	
	public SQLDefDelete() {
		this.init();
	}
	
	public SQLDefDelete(String table) {
		this.init(); 
		this.table(table);
	}
	
	protected void init() {
		this.conditions = new ArrayList<SQLDefCondition>();
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
			SysUtil.error("Can not execute the delete statement.");
			return new DBResult(statement, this.type(), e);
		}
	}

	/* 
	 * @see tz.pdb.api.base.DBCreate#create()
	 */
	@Override
	public String built() {
		String s = "DELETE ";
		s += this.table;
		if (this.conditions.size() > 0) {
			s += " WHERE ";
			StringBuilder con = new StringBuilder();
			this.conditions.forEach((condition) -> {
				con.append(" AND (" + condition.built() + " )");
			});
			s += con.substring(5).toString();
		}
		return s + ";";
	}

	/* 
	 * @see tz.pdb.api.base.DBUpdating#execute()
	 */
	@Override
	public int execute() {
		try {
			return this.driver().execute().executeUpdate(this.statement());
		} catch (SQLException e) {
			SysUtil.error("Can not execute the delete statement.");
			return -1;
		}
	}

	/* 
	 * @see tz.pdb.api.DBDelete#table()
	 */
	@Override
	public String table() {
		return this.table;
	}

	/* 
	 * @see tz.pdb.api.DBDelete#table(java.lang.String)
	 */
	@Override
	public DBDelete table(String table) {
		this.table = table;
		return this;
	}

	/* 
	 * @see tz.pdb.api.DBDelete#where(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public DBCondition where(String one, String two, String equal) {
		SQLDefCondition condition = new SQLDefCondition(one, two, equal, null);
		this.conditions.add(condition);
		return condition;
	}
	
	/* 
	 * @see tz.pdb.api.statments.SQLiteStatement#ident()
	 */
	@Override
	public String ident() {
		return "DB::Driver::SQLite::Delete";
	}

}
