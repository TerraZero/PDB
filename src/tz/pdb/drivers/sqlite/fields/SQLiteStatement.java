package tz.pdb.drivers.sqlite.fields;

import java.util.HashMap;
import java.util.Map;

import tz.core.logger.Log;
import tz.pdb.SQLPlaceholder;
import tz.pdb.api.DBDriver;
import tz.pdb.api.base.DBStatement;

/**
 * 
 * @author terrazero
 * @created May 11, 2015
 * 
 * @file SQLiteStatement.java
 * @project PDB
 * @identifier tz.pdb.api.statments
 *
 */
public abstract class SQLiteStatement implements DBStatement {
	
	private DBDriver driver;
	private Map<String, String> placeholders;
	
	public SQLiteStatement() {
		this.placeholders = new HashMap<String, String>();
	}

	/* 
	 * @see tz.pdb.api.base.DBStatement#driver()
	 */
	@Override
	public DBDriver driver() {
		return this.driver;
	}

	/* 
	 * @see tz.pdb.api.base.DBStatement#driver(tz.pdb.api.driver.DBDriver)
	 */
	@Override
	public DBStatement driver(DBDriver driver) {
		this.driver = driver;
		return this;
	}
	
	/* 
	 * @see tz.pdb.api.base.DBStatement#ident()
	 */
	@Override
	public String ident() {
		return Log.ident("DB", "Driver", "SQLite", "Statement");
	}
	
	/* 
	 * @see tz.pdb.api.base.DBStatement#statement()
	 */
	@Override
	public String statement() {
		return SQLPlaceholder.generic(this.built(), this.placeholders, this.ident());
	}
	
	public DBStatement placeholder(String placeholder, String value) {
		this.placeholders.put(placeholder, value);
		return this;
	}

}
