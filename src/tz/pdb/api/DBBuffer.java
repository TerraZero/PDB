package tz.pdb.api;

import java.util.HashMap;
import java.util.Map;

import tz.core.logger.Log;
import tz.pdb.DB;
import tz.pdb.SQLPlaceholder;
import tz.pdb.api.base.DBStatement;

public class DBBuffer {

	private String buffer;
	private DBStatement statment;
	private Map<String, String> placeholders;
	
	public DBBuffer(DBStatement statement) {
		this.statment = statement;
		this.placeholders = new HashMap<String, String>();
		this.rebuilt();
	}
	
	public DBBuffer rebuilt() {
		DB.extend(this.statment);
		this.buffer = this.statment.built();
		return this;
	}
	
	public DBStatement statement() {
		return this.statment;
	}
	
	public DBBuffer vars(String key, String value) {
		this.placeholders.put(key, value);
		return this;
	}
	
	public String buffer() {
		return this.buffer;
	}
	
	public DBResult execute() {
		return this.execute(true);
	}

	public DBResult execute(boolean clear) {
		String statement = SQLPlaceholder.generic(this.buffer, this.placeholders, this.ident());
		if (clear) {
			this.placeholders.clear();
		}
		return this.statment.driver().execute(this.statment.type(), statement);
	}
	
	public String built() {
		return this.built(false);
	}
	
	public String built(boolean clear) {
		String built = SQLPlaceholder.generic(this.buffer, this.placeholders, this.ident());
		if (clear) {
			this.placeholders.clear();
		}
		return built;
	}
	
	public String ident() {
		return Log.ident("DB", "API", "DBBuffer", this.statment.type());
	}
	
}
