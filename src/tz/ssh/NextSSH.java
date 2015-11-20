package tz.ssh;

import com.jcraft.jsch.JSch;

public class NextSSH {

	private static JSch jsch;
	
	public static JSch getJSch() {
		if (NextSSH.jsch == null) {
			NextSSH.jsch = new JSch();
		}
		return NextSSH.jsch;
	}
	
}
