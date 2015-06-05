package tz.pdb.drivers.sqlite.statements;

import java.sql.ResultSet;
import java.sql.SQLException;

import tz.core.logger.Log;
import tz.pdb.api.functions.DBResult;
import tz.pdb.api.statements.DBQuery;
import tz.pdb.drivers.sqlite.fields.SQLiteStatement;

public class SQLiteQuery extends SQLiteStatement implements DBQuery {
	
	private String query;
	
	public SQLiteQuery() {
	}
	
	public SQLiteQuery(String query) {
		this.query = query;
	}

	@Override
	public ResultSet execute() {
		try {
			return this.driver().execute().executeQuery(this.statement());
		} catch (SQLException e) {
			Log.fatal(this.ident(), "Can not execute the query statement.");
			return null;
		}
	}
	
	@Override
	public String ident() {
		return Log.ident("DB", "Driver", "SQLite", "Query");
	}

	@Override
	public DBResult exe() {
		String statement = this.statement();
		try {
			return new DBResult(statement, this.type(), this.driver().execute().executeQuery(statement));
		} catch (SQLException e) {
			Log.fatal(this.ident(), "Can not execute the query statement.");
			return new DBResult(statement, this.type(), e);
		}
	}

	@Override
	public String built() {
		return this.query;
	}

	@Override
	public String query() {
		return this.query;
	}

	@Override
	public DBQuery query(String query) {
		this.query = query;
		return this;
	}

}
