package tz.io.pdb.drivers.sql.statements;

import java.sql.ResultSet;
import java.sql.SQLException;

import tz.io.pdb.api.functions.DBResult;
import tz.io.pdb.api.statements.DBQuery;
import tz.io.pdb.drivers.sql.fields.SQLStatement;
import tz.sys.Sys;

public class SQLQuery extends SQLStatement implements DBQuery {
	
	private String query;
	
	public SQLQuery() {
	}
	
	public SQLQuery(String query) {
		this.query = query;
	}

	@Override
	public ResultSet execute() {
		try {
			return this.driver().execute().executeQuery(this.statement());
		} catch (SQLException e) {
			Sys.error("Can not execute the query statement.");
			return null;
		}
	}
	
	@Override
	public String ident() {
		return "DB::Driver::SQLite::Query";
	}

	@Override
	public DBResult exe() {
		String statement = this.statement();
		try {
			return new DBResult(statement, this.type(), this.driver().execute().executeQuery(statement));
		} catch (SQLException e) {
			Sys.error("Can not execute the query statement.");
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
