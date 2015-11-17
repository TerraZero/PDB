package tz.pdb.api.functions;

import java.util.HashMap;
import java.util.Map;

import tz.pdb.DB;
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
		String statement = DBPlaceholder.generic(this.buffer, this.placeholders, this.ident());
		if (clear) {
			this.placeholders.clear();
		}
		return this.statment.driver().execute(this.statment.type(), statement);
	}
	
	public String built() {
		return this.built(false);
	}
	
	public String built(boolean clear) {
		String built = DBPlaceholder.generic(this.buffer, this.placeholders, this.ident());
		if (clear) {
			this.placeholders.clear();
		}
		return built;
	}
	
	public String ident() {
		return "DB::API::DBBuffer::" + this.statment.type();
	}
	
}
