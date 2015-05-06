package tz.pdb;

import tz.pdb.api.DBSelect;

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

	public static void main(String[] args) {
		DBSelect select = DB.select();
		select.fields("n", "node", "test AS t").where("n.status", "0").and("n.title", "cool");
		select.join("inner", "table AS t", "t.id", "n.id").and("t.status", "0");
		select.order("n.id", "ASC").desc("n.num").asc("t.num");
		select.limit(0, 5);
		select.execute();
	}
	
	public static DBSelect select() {
		return null;
	}
	
}
