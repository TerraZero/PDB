package tz.pdb;

import tz.pdb.api.DBExtender;
import tz.pdb.api.base.DBExtendData;
import tz.pdb.api.base.DBStatement;
import tz.pdb.api.fields.DBJoin;
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
				System.out.println(type);
				DBSelect select = statement.child();
				String node = select.table("node");
				select.join("inner", "access", this.alias("access"), "n.nid", DBJoin.KEY + ".nid");
			}
			
		});
		
		DBSelect select = DB.select();
		select.extend("access");
		select.from("node", "n").fields("n", "nid", "name", "type");
		System.out.println(select.statement());
	}
	
}
