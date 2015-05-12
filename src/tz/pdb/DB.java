package tz.pdb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import tz.core.logger.Log;
import tz.pdb.api.DBInsert;
import tz.pdb.api.DBSelect;
import tz.pdb.api.DBTable;
import tz.pdb.api.DBUpdate;
import tz.pdb.api.driver.DBDriver;
import tz.pdb.drivers.sqlite.SQLiteDriver;

/**
 * 
 * @author terrazero
 * @created Apr 8, 2015
 * 
 * @file SysSQL.java
 * @project SysSQL
 * @identifier TZ.sql
 *
 */
public class DB {
	
	public static final String DEFAULT_DB = "default";
	
	private static Map<String, DB> dbs;
	
	public static void create(String name, String host, String user, String password, DBDriver driver) {
		if (DB.dbs == null) {
			DB.dbs = new HashMap<String, DB>();
		}
		DB.dbs.put(name, new DB(host, user, password, driver));
	}
	
	public static DB get(String name) {
		try {
			return DB.dbs.get(name);
		} catch (NullPointerException e) {
			Log.fatal("DB", "No connection has been established or no connection with the name [0]", name);
		}
		return null;
	}

	public static void main(String[] args) {
		DB.create(DB.DEFAULT_DB, "db/test.db", null, null, new SQLiteDriver());
		DBDriver d = DB.driver();
		// DBTable table = d.table("T_test").field("id", "INTEGER PRIMARY KEY AUTOINCREMENT").field("created", "int").field("name", "varchar", 255, "NOT NULL");
		// DBInsert insert = d.insert("T_test").cols("created", "name").row("#5054", ":Paul").row("#478965", ":Nico").row("#4789", ":Georg");
		/*
		DBSelect select = d.select("T_test", "t").fields("t", "id", "name");
		select.where("t.created", "#test", "<");
		select.placeholder("#test", "10000");
		ResultSet result = select.execute();
		try {
			while (result.next()) {
				System.out.println(result.getInt("id") + " : " + result.getString("name"));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		//*/
		DBUpdate update = d.update();
		update.table("T_test").set("name", ":Cool").where("id", "1");
		System.out.println(update.statement());
	}
	
	public static DBSelect select() {
		return DB.select(DB.DEFAULT_DB);
	}
	
	public static DBSelect select(String db) {
		return DB.get(db).dbDriver().select();
	}
	
	public static DBTable table() {
		return DB.table(DB.DEFAULT_DB);
	}
	
	public static DBTable table(String db) {
		return DB.get(db).dbDriver().table();
	}
	
	public static DBInsert insert() {
		return DB.insert(DB.DEFAULT_DB);
	}
	
	public static DBInsert insert(String db) {
		return DB.get(db).dbDriver().insert();
	}
	
	public static DBDriver driver() {
		return DB.driver(DB.DEFAULT_DB);
	}
	
	public static DBDriver driver(String db) {
		return DB.get(db).dbDriver();
	}
	
	private String host;
	private String user;
	private String pass;
	private DBDriver driver;
	
	public DB(String host, String user, String pass, DBDriver driver) {
		this.host = host;
		this.user = user;
		this.pass = pass;
		this.driver = driver;
		this.driver.connect(host, user, pass);
	}
	
	public String host() {
		return this.host;
	}
	
	public String user() {
		return this.user;
	}
	
	public String pass() {
		return this.pass;
	}
	
	public DBDriver dbDriver() {
		return this.driver;
	}
	
}
