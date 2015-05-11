package tz.pdb;

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
	
	private static DB db;
	
	public static void create(String host, String user, String password, DBDriver driver) {
		DB.db = new DB(host, user, password, driver);
	}

	public static void main(String[] args) {
		DB.create("db/test.db", "test", "sdfhsdf", new SQLiteDriver());
		DBSelect select = DB.select();
		select.from("node", "n").fields("n", "status", "nid").join("inner", "field_data", "fd", "fd.nid", "n.nid").and("n.status", "hallo").or("ok", "Ok");
		select.join("left", "testtable", "tt", "tt.t", "tn.n");
		select.where("test", "ok", "=").and("cool", "sdhf", ">").or("test", "shdfj", "<");
		select.where("sjfdh", "jsdhfjkfh", "NONE");
		System.out.println(select.create());
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
		return DB.db.driver.select();
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
	
}
