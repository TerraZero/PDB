package tz.pdb;

public class SQLPlaceholderExecuter {
	
	private String subject;
	
	public SQLPlaceholderExecuter(String subject) {
		this.subject = subject;
	}
	
	public String subject() {
		return this.subject;
	}
	
	public SQLPlaceholderExecuter execute(String key, String value) {
		this.subject = this.subject.replaceAll("#" + key + "#", value);
		return this;
	}
	
}