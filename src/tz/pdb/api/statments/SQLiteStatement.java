package tz.pdb.api.statments;

import tz.pdb.api.base.DBStatement;
import tz.pdb.api.driver.DBDriver;

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

}
