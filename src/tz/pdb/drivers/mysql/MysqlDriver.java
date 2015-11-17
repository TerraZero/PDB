package tz.pdb.drivers.mysql;

import java.sql.DriverManager;
import java.sql.SQLException;

import tz.pdb.drivers.def.SQLDefDriver;
import tz.sys.SysUtil;

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
public class MySQLDriver extends SQLDefDriver {

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
			SysUtil.log("Connect to mysql database via host [0] and user [1] successfully!", host, user);
		} catch (SQLException e) {
			SysUtil.error("Can not connect to host [0] with the user [1]!", host, user);
			SysUtil.exception(e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
	
