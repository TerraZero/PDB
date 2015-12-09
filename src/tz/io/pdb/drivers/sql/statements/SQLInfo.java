package tz.io.pdb.drivers.sql.statements;

import java.sql.ResultSet;
import java.sql.SQLException;

import tz.io.pdb.api.base.DBStatement;
import tz.io.pdb.api.functions.DBResult;
import tz.io.pdb.api.statements.DBInfo;
import tz.io.pdb.drivers.sql.fields.SQLStatement;
import tz.sys.Sys;

public class SQLInfo extends SQLStatement implements DBInfo {
	
	private String[] databases;
	private String[] tables;
	
	private String use;

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
			if (this.use == null) {
				Sys.error("Database is not defined so it can not resolve the tables!");
				return null;
			}
			ResultSet result = null;
			SQLQuery query = null;
			
			query = new SQLQuery("SELECT COUNT(table_name) FROM information_schema.tables WHERE table_schema = '" + this.use + "'");
			query.driver(this.driver());
			result = query.execute();
			try {
				if (result.next()) {
					this.tables = new String[result.getInt(1)];
				}
			} catch (SQLException e) {
				Sys.error("Can not read the count of tables!");
			}
			
			query.query("SELECT table_name FROM information_schema.tables WHERE table_schema = '" + this.use + "'");
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
		Sys.warn("Not implement!");
		return null;
	}

	@Override
	public DBInfo use(String database) {
		if (!database.equals(this.use)) {
			SQLQuery query = new SQLQuery("USE " + database);
			query.driver(this.driver());
			DBResult r = query.exe();
			if (r.success()) {
				this.use = database;
			} else {
				Sys.exception(r.exception());
			}
		}
		return this;
	}

	@Override
	public String use() {
		return this.use;
	}

	@Override
	public String type() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] databases(boolean force) {
		if (force || this.databases == null) {
			ResultSet result = null;
			SQLQuery query = null;
			
			query = new SQLQuery("SELECT COUNT(*) FROM information_schema.SCHEMATA WHERE schema_name not in ('mysql','information_schema')");
			query.driver(this.driver());
			result = query.execute();
			try {
				if (result.next()) {
					this.databases = new String[result.getInt(1)];
				}
			} catch (SQLException e) {
				Sys.error("Can not read the count of databases!");
			}
			
			query.query("SELECT schema_name FROM information_schema.SCHEMATA WHERE schema_name not in ('mysql','information_schema')");
			result = query.execute();
			try {
				int i = 0;
				while (result.next()) {
					this.databases[i++] = result.getString(1);
				}
			} catch (SQLException e) {
				Sys.error("Can not read the databases!");
			}
		}
		return this.databases;
	}

}
