package tz.io.pdb.drivers.mysql;

import java.sql.DriverManager;
import java.sql.SQLException;

import tz.io.pdb.api.DBAPIDriver;
import tz.net.ssh.SSHConnection;
import tz.sys.Sys;

@DBAPIDriver(name="sshmysql")
public class SSHMySQLDriver extends MySQLDriver {
	
	private SSHConnection ssh;

	public String ident() {
		return "DB::Driver::Def::SSH::MySQL";
	}
	
	/* 
	 * @see tz.pdb.api.driver.DBDriver#connect(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean connect(String host, String user, String pass) {
		try {
			String[] data = host.split("::");
			this.ssh = new SSHConnection();
			this.ssh.connection(data[0], data[1], data[2], 22);
			if (!this.ssh.connecting().success()) {
				Sys.error("Can not connect to host [0] via SSH connection!", data[0]);
				return false;
			}
			this.ssh.service(12345, "localhost", 3306);
			try {
				this.connection = DriverManager.getConnection("jdbc:mysql://localhost:" + this.ssh.servicePort() +  "?user=" + user + "&password=" + pass);
				Sys.log("Connect to mysql database via SSH host [0] and user [1] successfully!", data[0], user);
			} catch (SQLException e) {
				Sys.error("Can not connect to SSH host [0] with the user [1]!", data[0], user);
				Sys.exception(e);
				e.printStackTrace();
				return false;
			}
		} catch (Exception e) {
			Sys.error("Unexpected error by connection to SSH MySQL");
			Sys.exception(e);
			return false;
		}
		return true;
	}
	
}
