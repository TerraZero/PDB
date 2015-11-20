package tz.ssh;

import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import tz.sys.reflect.annot.Loader;

@Loader(triggers={"sysexit"}, function="exit")
public class SSHConnection {
	
	private static List<SSHConnection> connections;
	
	static {
		SSHConnection.connections = new ArrayList<SSHConnection>();
	}
	
	public static void exit(String trigger) {
		for (SSHConnection c : SSHConnection.connections) {
			if (c.connected()) {
				c.disconnect();
			}
		}
	}
	
	private Session session;
	private int servicePort;
	private JSchException exception;
	
	public SSHConnection() {
		SSHConnection.connections.add(this);
	}
	
	public SSHConnection connection(String host) {
		try {
			this.session = NextSSH.getJSch().getSession(host);
		} catch (JSchException e) {
			this.exception = e;
		}
		return this;
	}
	
	public SSHConnection connection(String host, String user) {
		try {
			this.session = NextSSH.getJSch().getSession(user, host);
		} catch (JSchException e) {
			this.exception = e;
		}
		return this;
	}
	
	public SSHConnection connection(String host, String user, int port) {
		try {
			this.session = NextSSH.getJSch().getSession(user, host, port);
		} catch (JSchException e) {
			this.exception = e;
		}
		return this;
	}
	
	public SSHConnection connection(String host, String user, String pass, int port) {
		try {
			this.session = NextSSH.getJSch().getSession(user, host, port);
			this.session.setPassword(pass);
			this.session.setConfig("StrictHostKeyChecking", "no");
		} catch (JSchException e) {
			this.exception = e;
		}
		return this;
	}
	
	public SSHConnection connecting() {
		try {
			this.session.connect();
		} catch (JSchException e) {
			this.exception = e;
		}
		return this;
	}
	
	public SSHConnection connecting(String host, String user, String pass, int sshPort, int servicePort, String serviceBind, int serviceTarget) {
		this.connection(host, user, pass, sshPort).connecting();
		if (this.success()) {
			this.service(servicePort, serviceBind, serviceTarget);
		}
		return this;
	}
	
	/**
	 * Port Forwarding to connect a service over ssh.
	 * Get the forwarding port with getServicePort(target);
	 * @param local - the local port to bind the connection
	 * @param bind - the host to bind the connection default: "localhost"
	 * @param target - the target port to the service
	 * @return this
	 */
	public SSHConnection service(int local, String bind, int target) {
		try {
			this.servicePort = this.session.setPortForwardingL(local, bind, target);
		} catch (JSchException e) {
			this.exception = e;
		}
		return this;
	}
	
	public boolean connected() {
		return this.session.isConnected();
	}
	
	public boolean success() {
		return this.exception == null;
	}
	
	public int servicePort() {
		return this.servicePort;
	}
	
	public SSHConnection disconnect() {
		this.session.disconnect();
		return this;
	}

}
