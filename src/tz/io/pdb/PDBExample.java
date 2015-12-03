package tz.io.pdb;

import java.sql.ResultSet;
import java.sql.SQLException;

import tz.io.pdb.api.base.DBExtendData;
import tz.io.pdb.api.base.DBStatement;
import tz.io.pdb.api.fields.DBJoin;
import tz.io.pdb.api.functions.DBBuffer;
import tz.io.pdb.api.functions.DBExtender;
import tz.io.pdb.api.functions.DBResult;
import tz.io.pdb.api.statements.DBCreate;
import tz.io.pdb.api.statements.DBInsert;
import tz.io.pdb.api.statements.DBQuery;
import tz.io.pdb.api.statements.DBSelect;
import tz.io.pdb.drivers.mysql.MySQLDriver;
import tz.io.pdb.drivers.sqlite.SQLiteDriver;
import tz.sys.Sys;
import tz.sys.reflect.api.Program;
import tz.sys.vui.VUI;

/**
 * 
 * @author terrazero
 * @created May 28, 2015
 * 
 * @file PDBExample.java
 * @project PDB
 * @identifier tz.pdb
 *
 */
@Program(name="PDB Test", tags={"vui"})
public class PDBExample {
	
	public static void program() {
		VUI.debug(true);
		DB.create(DB.DEFAULT_DB, "zaheylu.me::terra::servzero0", "terra", "1234", "sshmysql");
		DBQuery q = DB.query().query("USE tz_next");
		q.exe();
		DBSelect s = DB.select();
		s.from("test", "t");
		s.fields("t", "id", "test");
		ResultSet r = s.execute();
		try {
			while (r.next()) {
				System.out.println(r.getString(1));
				System.out.println(r.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(s.statement());
		Sys.exit();
	}

//	public static void main(String[] args) {
//		DB.create(DB.DEFAULT_DB, "localhost", "test", "zes", "mysql");
//		DB.create(DB.DEFAULT_DB, "db/test.db", null, null, new SQLiteDriver());
//		DB.addExtender(new DBExtender() {
//			
//			@Override
//			public String[] type() {
//				return new String[] {DBSelect.TYPE};
//			}
//			
//			@Override
//			public String name() {
//				return "access";
//			}
//			
//			@Override
//			public void extend(String type, DBStatement statement, DBExtendData data) {
//				DBSelect select = statement.child();
//				select.join("inner", "access", this.alias("access"), "?[node].nid", DBJoin.KEY + ".nid");
//			}
//			
//		});
//		
//		DBSelect select = DB.select();
//		select.extend("access");
//		select.from("node", "n").fields("n", "nid", "name", "type").where("?[node].name", ":name").and("?[node].nid", "#nid");
//		DBBuffer buffer = new DBBuffer(select);
//		System.out.println(buffer.buffer());
//		System.out.println(buffer.vars(":name", "Paul").built(true));
//		System.out.println(buffer.vars("#nid", "Nico").built(true));
//		System.out.println(buffer.vars(":name", "Georg").built(true));
//		System.out.println(buffer.vars(":name", "Franky").built(true));
//		System.out.println(buffer.built(true));
//		buffer.vars(":name", "Test").vars("#nid", "5");
//		System.out.println(buffer.built(true));
//		
//		DBCreate c = DB.table().name("test").field("test", "VARCHAR", 255).field("id", "INT").keys("id");
//		System.out.println(c.built());
//		DBInsert i = DB.insert();
//		i.table("test").cols("test").row(":test");
//		System.out.println(i.built());
//		DBSelect s = DB.select();
//		s.selectAll().from("test", "t").where("?[test].test", "'test'");
//		System.out.println(s.built());
//	}
	
}
