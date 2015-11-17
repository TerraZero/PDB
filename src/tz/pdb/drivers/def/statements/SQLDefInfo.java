package tz.pdb.drivers.def.statements;

import java.sql.ResultSet;
import java.sql.SQLException;

import tz.pdb.api.base.DBStatement;
import tz.pdb.api.functions.DBResult;
import tz.pdb.api.statements.DBInfo;
import tz.pdb.drivers.def.fields.SQLDefStatement;
import tz.sys.SysUtil;

public class SQLDefInfo extends SQLDefStatement implements DBInfo {
	
	private String[] tables;

	@Override
	public DBResult exe() {
		SysUtil.warn("Info can not use the exe method!");
		return null;
	}
	
	@Override
	public String built() {
		SysUtil.warn("Info can not use the built method!");
		return null;
	}
	
	@Override
	public String statement() {
		SysUtil.warn("Info can not use the statement method!");
		return null;
	}
	
	@Override
	public DBStatement placeholder(String placeholder, String value) {
		SysUtil.warn("Info can not use the placeholder method!");
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
			SQLDefQuery query = null;
			
			query = new SQLDefQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = 'table'");
			query.driver(this.driver());
			result = query.execute();
			try {
				if (result.next()) {
					this.tables = new String[result.getInt(1)];
				}
			} catch (SQLException e) {
				SysUtil.error("Can not read the count of tables!");
			}
			
			query.query("SELECT name AS name FROM sqlite_master WHERE type = 'table'");
			result = query.execute();
			try {
				int i = 0;
				while (result.next()) {
					this.tables[i++] = result.getString(1);
				}
			} catch (SQLException e) {
				SysUtil.error("Can not read the tables!");
			}
		}
		return this.tables;
	}

	@Override
	public String autoIncrement() {
		SysUtil.warn("SQLite have not a autoincrements state!");
		return "";
	}

}
