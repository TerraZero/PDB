package tz.pdb;

import tz.pdb.api.base.DBExtendData;
import tz.pdb.api.base.DBStatement;
import tz.pdb.api.fields.DBJoin;
import tz.pdb.api.functions.DBBuffer;
import tz.pdb.api.functions.DBExtender;
import tz.pdb.api.statements.DBCreate;
import tz.pdb.api.statements.DBInsert;
import tz.pdb.api.statements.DBSelect;
import tz.pdb.drivers.sqlite.SQLiteDriver;

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
public class PDBExample {

	public static void main(String[] args) {
		DB.create(DB.DEFAULT_DB, "db/test.db", null, null, new SQLiteDriver());
		DB.addExtender(new DBExtender() {
			
			@Override
			public String[] type() {
				return new String[] {DBSelect.TYPE};
			}
			
			@Override
			public String name() {
				return "access";
			}
			
			@Override
			public void extend(String type, DBStatement statement, DBExtendData data) {
				DBSelect select = statement.child();
				select.join("inner", "access", this.alias("access"), "?[node].nid", DBJoin.KEY + ".nid");
			}
			
		});
		
		DBSelect select = DB.select();
		select.extend("access");
		select.from("node", "n").fields("n", "nid", "name", "type").where("?[node].name", ":name").and("?[node].nid", "#nid");
		DBBuffer buffer = new DBBuffer(select);
		System.out.println(buffer.buffer());
		System.out.println(buffer.vars(":name", "Paul").built(true));
		System.out.println(buffer.vars("#nid", "Nico").built(true));
		System.out.println(buffer.vars(":name", "Georg").built(true));
		System.out.println(buffer.vars(":name", "Franky").built(true));
		System.out.println(buffer.built(true));
		buffer.vars(":name", "Test").vars("#nid", "5");
		System.out.println(buffer.built(true));
		
		DBCreate c = DB.table().name("test").field("test", "VARCHAR", 255).field("id", "INT").keys("id");
		System.out.println(c.built());
		DBInsert i = DB.insert();
		i.table("test").cols("test").row(":test");
		System.out.println(i.built());
		DBSelect s = DB.select();
		s.selectAll().from("test", "t").where("?[test].test", "'test'");
		System.out.println(s.built());
	}
	
}
