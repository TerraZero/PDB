package tz.io.pdb.drivers.sqlite.statement;

import java.sql.ResultSet;
import java.sql.SQLException;

import tz.io.pdb.api.base.DBStatement;
import tz.io.pdb.api.functions.DBResult;
import tz.io.pdb.api.statements.DBInfo;
import tz.io.pdb.drivers.sql.fields.SQLStatement;
import tz.io.pdb.drivers.sql.statements.SQLQuery;
import tz.sys.Sys;

public class SQLiteInfo extends SQLStatement implements DBInfo {
	
	private String[] tables;

	@Override
	public DBResult exe() {
		Sys.warn("Info can not use the exe method!");
		return null;
	}
	
	@Override
	public String built() {
		Sys.warn("Info can not use the built method!");
		return null;
	}
	
	@Override
	public String statement() {
		Sys.warn("Info can not use the statement method!");
		return null;
	}
	
	@Override
	public DBStatement placeholder(String placeholder, String value) {
		Sys.warn("Info can not use the placeholder method!");
		return this;
	}
	
	@Override
	public String ident() {
		return "DB::Driver::SQLite::Info";
	}

	@Override
	public String[] tables(boolean force) {
		if (force || this.tables == null) {
			ResultSet result = null;
			SQLQuery query = null;
			
			query = new SQLQuery("SELECT COUNT(*) FROM sqlite_master AS m WHERE m.type = 'table'");
			query.driver(this.driver());
			result = query.execute();
			try {
				if (result.next()) {
					this.tables = new String[result.getInt(1)];
				}
			} catch (SQLException e) {
				Sys.error("Can not read the count of tables!");
			}
			
			query.query("SELECT name AS name FROM sqlite_master AS m WHERE m.type = 'table'");
			result = query.execute();
			try {
				int i = 0;
				while (result.next()) {
					this.tables[i++] = result.getString(1);
				}
			} catch (SQLException e) {
				Sys.error("Can not read the tables!");
			}
		}
		return this.tables;
	}

	@Override
	public String autoIncrement() {
		Sys.warn("SQLite have not a autoincrements state!");
		return null;
	}

	@Override
	public DBInfo use(String database) {
		Sys.warn("Info can not use the use method!");
		return null;
	}

	@Override
	public String use() {
		Sys.warn("Info can not use the use method!");
		return null;
	}

	@Override
	public String[] databases(boolean force) {
		Sys.warn("Info can not use the databases method!");
		return null;
	}

}
