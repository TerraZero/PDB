package tz.pdb.drivers.sqlite.statements;

import java.sql.ResultSet;
import java.sql.SQLException;

import tz.core.logger.Log;
import tz.pdb.api.base.DBStatement;
import tz.pdb.api.statements.DBInfo;
import tz.pdb.drivers.sqlite.fields.SQLiteStatement;

public class SQLiteInfo extends SQLiteStatement implements DBInfo {
	
	private String[] tables;

	@Override
	public void exe() {
		Log.warning(this.ident(), "Info can not use the exe method");
	}
	
	@Override
	public String built() {
		Log.warning(this.ident(), "Info can not use the built method");
		return null;
	}
	
	@Override
	public String statement() {
		Log.warning(this.ident(), "Info can not use the statement method");
		return null;
	}
	
	@Override
	public DBStatement placeholder(String placeholder, String value) {
		Log.warning(this.ident(), "Info can not use the placeholder method");
		return this;
	}
	
	@Override
	public String ident() {
		return Log.ident("DB", "Driver", "SQLite", "Info");
	}

	@Override
	public String[] tables(boolean force) {
		if (force || this.tables == null) {
			ResultSet result = null;
			SQLiteQuery query = null;
			
			query = new SQLiteQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = 'table'");
			query.driver(this.driver());
			result = query.execute();
			try {
				if (result.next()) {
					this.tables = new String[result.getInt(1)];
				}
			} catch (SQLException e) {
				Log.fatal(this.ident(), "Can not read the count of tables.");
			}
			
			query.query("SELECT name AS name FROM sqlite_master WHERE type = 'table'");
			result = query.execute();
			try {
				int i = 0;
				while (result.next()) {
					this.tables[i++] = result.getString(1);
				}
			} catch (SQLException e) {
				Log.fatal(this.ident(), "Can not read the tables.");
			}
		}
		return this.tables;
	}

	@Override
	public String autoIncrement() {
		Log.warning(this.ident(), "SQLite have not a autoincrements state!");
		return "";
	}

}
