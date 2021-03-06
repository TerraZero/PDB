package tz.io.pdb.drivers.sql.statements;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tz.io.pdb.api.fields.DBRow;
import tz.io.pdb.api.functions.DBResult;
import tz.io.pdb.api.statements.DBInsert;
import tz.io.pdb.drivers.sql.fields.SQLRow;
import tz.io.pdb.drivers.sql.fields.SQLStatement;
import tz.sys.Sys;

public class SQLInsert extends SQLStatement implements DBInsert {
	
	private String table;
	private String[] cols;
	private List<DBRow> rows;
	
	public SQLInsert() {
		this.init();
	}
	
	public SQLInsert(String table) {
		this.init();
		this.table(table);
	}
	
	protected void init() {
		this.rows = new ArrayList<DBRow>();
	}

	/* 
	 * @see tz.pdb.api.statments.SQLiteStatement#ident()
	 */
	@Override
	public String ident() {
		return "DB::Driver::SQLite::Insert";
	}

	@Override
	public String built() {
		StringBuilder string = new StringBuilder("INSERT INTO ");
		string.append(this.table).append(" ");
		if (this.cols != null) {
			string.append("(");
			for (String col : this.cols) {
				string.append(col).append(", ");
			}
			string.setLength(string.length() - 2);
			string.append(") ");
		}
		string.append("VALUES ");
		this.rows.forEach((row) -> {
			string.append(row.built()).append(", ");
		});
		string.setLength(string.length() - 2);
		return string.toString() + ";";
	}

	@Override
	public DBInsert table(String table) {
		this.table = table;
		return this;
	}

	@Override
	public DBInsert cols(String... columns) {
		this.cols = columns;
		return this;
	}

	@Override
	public DBInsert row(String... columns) {
		this.rows.add(new SQLRow(columns));
		return this;
	}

	@Override
	public String table() {
		return this.table;
	}

	@Override
	public String[] cols() {
		return this.cols;
	}

	@Override
	public List<DBRow> rows() {
		this.rows.forEach((row) -> {
			row.setHead(this.cols);
		});
		return this.rows;
	}

	@Override
	public int execute() {
		try {
			return this.driver().execute().executeUpdate(this.statement());
		} catch (SQLException e) {
			Sys.log("Can not execute the insert statement.");
			return -1;
		}
	}
	
	@Override
	public DBResult exe() {
		String statement = this.statement();
		try {
			return new DBResult(statement, this.type(), this.driver().execute().executeUpdate(statement));
		} catch (SQLException e) {
			Sys.log("Can not execute the insert statement.");
			return new DBResult(statement, this.type(), e);
		}
	}

}
