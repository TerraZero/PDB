package tz.pdb.drivers.sqlite;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import tz.pdb.drivers.sql.SQLDriver;
import tz.sys.SysUtil;

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
				SysUtil.log("Connect to sqlite database via host [0] successfully", host);
			} else {
				this.connection = DriverManager.getConnection("jdbc:sqlite:" + host, prop);
				SysUtil.log("Connect to sqlite database via host [0] and user [1] successfully!", host, user);
			}
		} catch (SQLException e) {
			SysUtil.error("Can not connect to host [0] with the user [1]!", host, user);
			SysUtil.exception(e);
			return false;
		}
		return true;
	}
	
}
