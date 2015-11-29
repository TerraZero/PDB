package tz.io.pdb.api.functions;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBResult {

	private String statement;
	private String type;
	
	private ResultSet result;
	private int updated;
	private boolean executed;
	
	private SQLException exception;
	
	public DBResult(String statement, String type, ResultSet result) {
		this.statement = statement;
		this.type = type;
		this.result = result;
	}
	
	public DBResult(String statement, String type, int updated) {
		this.statement = statement;
		this.type = type;
		this.updated = updated;
	}
	
	public DBResult(String statement, String type, boolean executed) {
		this.statement = statement;
		this.type = type;
		this.executed = executed;
	}
	
	public DBResult(String statement, String type, SQLException exception) {
		this.statement = statement;
		this.type = type;
		this.exception = exception;
	}
	
	public String statement() {
		return this.statement;
	}
	
	public String type() {
		return this.type;
	}
	
	public ResultSet result() {
		return this.result;
	}
	
	public int updated() {
		return this.updated;
	}
	
	public boolean executed() {
		return this.executed;
	}
	
	public boolean success() {
		return this.exception == null;
	}
	
	public boolean error() {
		return this.exception != null;
	}
	
	public SQLException exception() {
		return this.exception;
	}
	
}
