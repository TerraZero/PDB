package tz.pdb.drivers;

import tz.pdb.DB;
import tz.pdb.api.statements.DBSelect;
import tz.pdb.drivers.mysql.MySQLDriver;

public class PDBMySQLTest {
 
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DB.create(DB.DEFAULT_DB, "zaheylu.me:3306/tz_next", "terra", "1234", new MySQLDriver());
		System.out.println("ok");
	}
	
}
