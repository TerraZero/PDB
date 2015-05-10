package tz.pdb;

import java.util.HashMap;
import java.util.Map;

import tz.core.logger.Log;
import tz.pdb.api.DBSelect;
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
		DB.create("db2", "db/test2.db", null, null, new SQLiteDriver());
		DBSelect select = DB.select();
		select.from("node", "n").fields("n", "status", "nid").join("inner", "field_data", "fd", "fd.nid", "n.nid").and("n.status", "hallo").or("ok", "Ok");
		select.where("test", "ok", "=").and("cool", "sdhf", ">").or("test", "shdfj", "<");
		select.where("sjfdh", "jsdhfjkfh", "NONE");
		System.out.println(select.execute());
		/*
		DBSelect select = DB.select();
		select.fields("n", "node", "test AS t").where("n.status", "0").and("n.title", "cool");
		select.join("inner", "table", "t", "t.id", "n.id").and("t.status", "0");
		select.order("n.id", "ASC").desc("n.num").asc("t.num");
		select.limit(0, 5);
		select.execute();
		//*/
	}
	
	public static DBSelect select() {
		return DB.select(DB.DEFAULT_DB);
	}
	
	public static DBSelect select(String db) {
		return DB.get(DB.DEFAULT_DB).driver().select();
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
	
	public DBDriver driver() {
		return this.driver;
	}
	
}
