package tz.pdb.drivers.sqlite.statements;

import java.sql.SQLException;

import tz.core.logger.Log;
import tz.pdb.api.DBResult;
import tz.pdb.api.base.DBStatement;
import tz.pdb.api.statements.DBOperation;
import tz.pdb.drivers.sqlite.fields.SQLiteStatement;

public class SQLiteOperation extends SQLiteStatement implements DBOperation {

	@Override
	public DBResult exe() {
		Log.warning(this.ident(), "Operation can not use the exe method");
		return null;
	}
	
	@Override
	public String ident() {
		return Log.ident("DB", "Driver", "SQLite", "Operation");
	}

	@Override
	public String built() {
		Log.warning(this.ident(), "Operation can not use the built method");
		return null;
	}
	
	@Override
	public String statement() {
		Log.warning(this.ident(), "Operation can not use the statement method");
		return null;
	}

	@Override
	public DBStatement placeholder(String placeholder, String value) {
		Log.warning(this.ident(), "Operation can not use the placeholder method");
		return this;
	}

	@Override
	public int truncate(String table) {
		String query = "TRUNCATE TABLE " + table;
		try {
			return this.driver().execute().executeUpdate(query);
		} catch (SQLException e) {
			Log.fatal(this.ident(), "Can not truncate the table [0].", table);
			return -1;
		}
	}

	@Override
	public int drop(String table) {
		String query = "DROP TABLE " + table;
		try {
			return this.driver().execute().executeUpdate(query);
		} catch (SQLException e) {
			Log.fatal(this.ident(), "Can not drop the table [0].", table);
			return -1;
		}
	}

}
