package tz.pdb.drivers.sqlite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tz.core.logger.Log;
import tz.pdb.api.DBInsert;
import tz.pdb.api.statments.DBRow;
import tz.pdb.api.statments.SQLiteStatement;
import tz.pdb.drivers.sqlite.statements.SQLiteRow;

public class SQLiteInsert extends SQLiteStatement implements DBInsert {
	
	private String table;
	private String[] cols;
	private List<DBRow> rows;
	
	public SQLiteInsert() {
		this.init();
	}
	
	public SQLiteInsert(String table) {
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
		return Log.ident("DB", "Driver", "SQLite", "Insert");
	}

	@Override
	public String create() {
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
			string.append(row.create()).append(", ");
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
		this.rows.add(new SQLiteRow(columns));
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
			Log.fatal(this.ident(), "Can not execute the insert statement.");
			return -1;
		}
	}
	
	@Override
	public void exe() {
		this.execute();
	}

}
