package tz.io.pdb.drivers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import tz.io.pdb.DB;
import tz.io.pdb.api.functions.DBResult;
import tz.io.pdb.api.statements.DBOperation;
import tz.io.pdb.api.statements.DBSelect;
import tz.io.pdb.drivers.mysql.MySQLDriver;

public class PDBMySQLTest {
 
	public static void main(String[] args) {
		JSch jsch = new JSch();
		int port = 0;
		Session session = null;
		try {
			session = jsch.getSession("terra", "zaheylu.me", 22);
			session.setPassword("servzero0");
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			port = session.setPortForwardingL(12345, "localhost", 3306);
		} catch (JSchException e1) {
			e1.printStackTrace();
		}
		
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
		DB.create(DB.DEFAULT_DB, "localhost:" + port + "/tz_next", "terra", "1234", new MySQLDriver());
		System.out.println("ok");
		DBOperation op = DB.operation();
		op.truncate("test");
		op.drop("test");
		DBSelect select = DB.select();
		select.selectAll().from("test", "t");
		DBResult r = select.exe();
		if (r.success()) {
			System.out.println("super");
			ResultSet s = r.result();
			try {
				for (; s.next();) {
					System.out.println(s.getInt("id"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("no");
		}
		session.disconnect();
	}
	
}
