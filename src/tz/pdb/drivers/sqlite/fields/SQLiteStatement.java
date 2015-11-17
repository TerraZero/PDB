package tz.pdb.drivers.sqlite.fields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tz.pdb.DB;
import tz.pdb.api.DBDriver;
import tz.pdb.api.base.DBExtendData;
import tz.pdb.api.base.DBStatement;
import tz.pdb.api.functions.DBPlaceholder;

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
	private List<DBExtendData> extend;
	
	public SQLiteStatement() {
		this.placeholders = new HashMap<String, String>();
		this.extend = new ArrayList<DBExtendData>();
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
		return "DB::Driver::SQLite::Statement";
	}
	
	/* 
	 * @see tz.pdb.api.base.DBStatement#statement()
	 */
	@Override
	public String statement() {
		DB.extend(this);
		return DBPlaceholder.generic(this.built(), this.placeholders, this.ident());
	}
	
	public DBStatement placeholder(String placeholder, String value) {
		this.placeholders.put(placeholder, value);
		return this;
	}
	
	/* 
	 * @see tz.pdb.api.base.DBStatement#extend(java.lang.String, tz.pdb.api.base.DBExtendData)
	 */
	@Override
	public DBStatement extend(String extend, DBExtendData data) {
		if (data == null) {
			data = new SQLiteExtendData();
		}
		this.extend.add(data.extend(extend));
		return this;
	}
	
	/* 
	 * @see tz.pdb.api.base.DBStatement#extend()
	 */
	@Override
	public List<DBExtendData> extend() {
		return this.extend;
	}

}
