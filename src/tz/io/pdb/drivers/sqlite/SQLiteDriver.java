package tz.io.pdb.drivers.sqlite;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import tz.io.pdb.api.DBAPIDriver;
import tz.io.pdb.api.statements.DBInfo;
import tz.io.pdb.drivers.sql.SQLDriver;
import tz.io.pdb.drivers.sqlite.statement.SQLiteInfo;
import tz.sys.Sys;

@DBAPIDriver(name="sqlite")
public class SQLiteDriver extends SQLDriver {

	public String ident() {
		return "DB::Driver::Def::SQLite";
	}
	
	/* 
	 * @see tz.pdb.api.driver.DBDriver#connect(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean connect(String host, String user, String pass) {
		Properties prop = null;
		if (user != null) {
			prop = new Properties();
			prop.put("user", user);
			if (pass != null) {
				prop.put("password", pass);
			}
		}
		try {
			if (prop == null) {
				this.connection = DriverManager.getConnection("jdbc:sqlite:" + host);
				Sys.log("Connect to sqlite database via host [0] successfully", host);
			} else {
				this.connection = DriverManager.getConnection("jdbc:sqlite:" + host, prop);
				Sys.log("Connect to sqlite database via host [0] and user [1] successfully!", host, user);
			}
		} catch (SQLException e) {
			Sys.error("Can not connect to host [0] with the user [1]!", host, user);
			Sys.exception(e);
			return false;
		}
		return true;
	}
	
	@Override
	public DBInfo info() {
		if (this.info == null) {
			this.info = new SQLiteInfo();
			this.info.driver(this);
		}
		return this.info;
	}
	
}
