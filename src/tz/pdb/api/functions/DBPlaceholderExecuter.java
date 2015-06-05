package tz.pdb.api.functions;

public class DBPlaceholderExecuter {
	
	private String subject;
	
	public DBPlaceholderExecuter(String subject) {
		this.subject = subject;
	}
	
	public String subject() {
		return this.subject;
	}
	
	public DBPlaceholderExecuter execute(String key, String value) {
		this.subject = this.subject.replaceAll("#" + key + "#", value);
		return this;
	}
	
}