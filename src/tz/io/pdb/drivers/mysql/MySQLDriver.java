package tz.io.pdb.drivers.mysql;

import java.sql.DriverManager;
import java.sql.SQLException;

import tz.io.pdb.drivers.sql.SQLDriver;
import tz.sys.Sys;
import tz.sys.reflect.annot.Loader;

/**
 * 
 * @author terrazero
 * @created Apr 8, 2015
 * 
 * @file MysqlDriver.java
 * @project SysSQL
 * @identifier TZ.sql.driver.mysql
 *
 */
@Loader(triggers={"DB"})
public class MySQLDriver extends SQLDriver {
	
	public static void init(String trigger) {
		System.out.println("ok");
	}

	public String ident() {
		return "DB::Driver::Def::MySQL";
	}
	
	/* 
	 * @see tz.pdb.api.driver.DBDriver#connect(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean connect(String host, String user, String pass) {
		try {
			this.connection = DriverManager.getConnection("jdbc:mysql://" + host + "?user=" + user + "&password=" + pass);
			Sys.log("Connect to mysql database via host [0] and user [1] successfully!", host, user);
		} catch (SQLException e) {
			Sys.error("Can not connect to host [0] with the user [1]!", host, user);
			Sys.exception(e);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public String name() {
		return "mysql";
	}
	
}
	
